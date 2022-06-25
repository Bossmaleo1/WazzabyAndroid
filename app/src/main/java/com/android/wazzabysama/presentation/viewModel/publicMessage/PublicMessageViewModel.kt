package com.android.wazzabysama.presentation.viewModel.publicMessage

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.domain.usecase.publicmessage.*
import kotlinx.coroutines.Dispatchers
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

    fun getPublicMessage(problematic: Problematic, page: Int, token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            //publicMessageList.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getPublicMessageUseCase.execute(problematic, page, "Bearer $token")
                    apiResult.data?.let {
                        publicMessageList.postValue(it.publicMessageList)
                        publicMessageStateRemoteList.addAll(it.publicMessageList)
                    }
                    //isRefreshing = true
                } else {
                    Toast.makeText(app.applicationContext,"Internet is not available",Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(app.applicationContext,e.message.toString(),Toast.LENGTH_LONG).show()
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun initPublicMessage() {
        publicMessageStateRemoteList.removeAll(publicMessageStateRemoteList)
    }
}