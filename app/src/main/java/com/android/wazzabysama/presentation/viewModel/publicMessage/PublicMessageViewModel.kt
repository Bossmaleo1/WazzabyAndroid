package com.android.wazzabysama.presentation.viewModel.publicMessage

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.domain.usecase.publicmessage.*
import com.android.wazzabysama.ui.UIEvent.Event.AuthEvent
import com.android.wazzabysama.ui.UIEvent.Event.PublicMessageEvent
import com.android.wazzabysama.ui.UIEvent.ScreenState.PublicMessageScreenState.PublicMessageScreenState
import com.android.wazzabysama.ui.UIEvent.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PublicMessageViewModel @Inject constructor(
    private val app: Application,
    private val deleteSavedPublicMessageUseCase: DeleteSavedPublicMessageUseCase,
    private val getPublicMessageUseCase: GetPublicMessageUseCase,
    private val getSavedPublicMessageUseCase: GetSavedPublicMessageUseCase,
    private val savePublicMessageUseCase: SavePublicMessageUseCase
) : AndroidViewModel(app) {

    private val publicMessageList: MutableLiveData<List<PublicMessage>> = MutableLiveData()
    val publicMessageListValue: LiveData<List<PublicMessage>> = publicMessageList

    val publicMessageStateRemoteList = mutableStateListOf<PublicMessage>()
    val currentPage : MutableState<Int> = mutableStateOf(1)

    private val _screenState = mutableStateOf(
        PublicMessageScreenState(
            publicMessageList = mutableStateListOf()
        )
    )

    val screenState: State<PublicMessageScreenState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun getPublicMessage(problematic: Problematic, token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            if (isNetworkAvailable(app)) {
                try {
                    val apiResult = getPublicMessageUseCase.execute(problematic, screenState.value.currentPage, "Bearer $token")
                    apiResult.data?.let {apiPublicMessageResponse ->
                        screenState.value.publicMessageList.addAll(apiPublicMessageResponse.publicMessageList)
                        if (apiPublicMessageResponse.publicMessageList.size < 10) {
                            _screenState.value = _screenState.value.copy(
                                isNetworkConnected = true,
                                isLoad = false,
                                isNetworkError = false,
                                initCall = screenState.value.initCall++,
                                currentPage = screenState.value.currentPage++
                            )
                        }
                    }

                } catch (e: Exception) {
                    _screenState.value = _screenState.value.copy(
                        isNetworkError = true,
                        isNetworkConnected = true,
                        isLoad = false
                    )
                }
            } else {
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = false,
                    isNetworkError = false,
                    isLoad = false
                )
            }
        }

    fun savePublicMessageRoom(publicMessage: PublicMessage, user: UserRoom) =
        viewModelScope.launch {
            publicMessage.user.id?.let { userId ->
                savePublicMessageUseCase.execute(
                    PublicMessageRoom(
                        publicMessage.id,
                        publicMessage.anonymous,
                        publicMessage.content,
                        "",
                        publicMessage.published.toString(),
                        publicMessage.state,
                        publicMessage.wording,
                        user.problematic_id.toString(),
                        userId
                    )
                )

            }
        }

    fun getAllPublicMessage(problematic: Problematic) = liveData {
        getSavedPublicMessageUseCase.execute(problematic.id.toString()).collect {
            emit(it)
        }
    }


    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network =
                connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
    }

    fun initPublicMessage() {
        screenState.value.publicMessageList.removeAll(screenState.value.publicMessageList)
    }

    fun onEvent(event: PublicMessageEvent) {
        when (event) {
            is PublicMessageEvent.SavePublicMessageRoom -> {
                viewModelScope.launch {
                    savePublicMessageUseCase.execute(
                        PublicMessageRoom(
                            event.publicMessage.id,
                            event.publicMessage.anonymous,
                            event.publicMessage.content,
                            "",
                            event.publicMessage.published.toString(),
                            event.publicMessage.state,
                            event.publicMessage.wording,
                            event.user.problematic_id.toString(),
                            event.user.id!!
                        )
                    )
                }
            }
            is PublicMessageEvent.GetAllPublicMessage -> {
                viewModelScope.launch {
                    getSavedPublicMessageUseCase.execute(event.problematic.id.toString()).collect {
                        screenState.value.publicMessageListRoom.addAll(it)
                    }
                }
            }
            is PublicMessageEvent.IsNetworkConnected -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.network_error)
                        )
                    )
                }
            }

            is PublicMessageEvent.IsNetworkError -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.is_connect_error)
                        )
                    )
                }
            }

            is PublicMessageEvent.InitPublicMessageState -> {
                _screenState.value = _screenState.value.copy(
                    isLoad = true,
                    currentPage = 1,
                    /*publicMessageList = mutableListOf(),
                    publicMessageListRoom = mutableListOf(),
                    initCall = 0,*/
                )
            }
            else -> {}
        }
    }
}