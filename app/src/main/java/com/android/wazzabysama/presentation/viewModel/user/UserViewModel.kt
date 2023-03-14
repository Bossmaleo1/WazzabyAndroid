package com.android.wazzabysama.presentation.viewModel.user

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.android.wazzabysama.R
import androidx.lifecycle.*
import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.Token
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.usecase.problematic.GetSavedProblematicUseCase
import com.android.wazzabysama.domain.usecase.problematic.SaveProblematicUseCase
import com.android.wazzabysama.domain.usecase.user.*
import com.android.wazzabysama.ui.UIEvent.Event.AuthEvent
import com.android.wazzabysama.ui.UIEvent.ScreenState.AuthScreenState.AuthScreenState
import com.android.wazzabysama.ui.UIEvent.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val app: Application,
    private val getUserUseCase: GetUserUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val saveProblematicUseCase: SaveProblematicUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getSavedProblematic: GetSavedProblematicUseCase,
    private val deleteSavedUserUseCase: DeleteSavedUserUseCase
) : AndroidViewModel(app) {


    val token: MutableLiveData<Resource<ApiTokenResponse>> = MutableLiveData()
    val user: MutableLiveData<Resource<ApiUserResponse>> = MutableLiveData()

    private val tokenMutable: MutableLiveData<TokenRoom> = MutableLiveData()
    val tokenValue: LiveData<TokenRoom> = tokenMutable

    private val userMutable: MutableLiveData<UserRoom> = MutableLiveData()
    val userValue: LiveData<UserRoom> = userMutable

    private val problematic: MutableLiveData<ProblematicRoom> = MutableLiveData()
    val problematicValue: LiveData<ProblematicRoom> = problematic

    private val _screenState = mutableStateOf(
        AuthScreenState(
            emailInputValue = "",
            passwordInputValue = "",
            token = mutableListOf()
        )
    )

    val screenState: State<AuthScreenState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()


    init {

    }

    fun getToken(userName: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        if (isNetworkAvailable(app)) {
            try {
                val apiResult = getTokenUseCase.execute(userName, password)
                apiResult.data?.let { apiTokenResponse ->
                    screenState.value.token =
                        mutableListOf(Token(id = 1, token = apiTokenResponse.token))
                }
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isLoad = true,
                    isNetworkError = false,
                    initCallToken = screenState.value.initCallToken++,
                    user = mutableListOf(),
                    currentPage = 1,
                )

                getUser(
                    userName = userName,
                    token = screenState.value.token[0].token
                )
            } catch (e:Exception) {
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

    fun getUser(userName: String, token: String) = viewModelScope.launch(Dispatchers.IO) {
        if (isNetworkAvailable(app)) {
            try {
                val apiResult = getUserUseCase.execute(userName, "Bearer $token")
                apiResult.data?.let { apiUserResponse ->
                    screenState.value.user = apiUserResponse.Users
                    viewModelScope.launch {
                        saveTokenUseCase.execute(
                            TokenRoom(
                                id = 1,
                                token = screenState.value.token[0].token
                            )
                        )

                        val problematic = screenState.value.user[0].problematic as Problematic
                        saveProblematicUseCase.execute(
                            ProblematicRoom(
                                problematic.id,
                                problematic.wording,
                                problematic.language,
                                problematic.icon
                            )
                        )
                        saveUserUseCase.execute(
                            UserRoom(
                                id = screenState.value.user[0].id,
                                online = screenState.value.user[0].online,
                                anonymous = screenState.value.user[0].anonymous,
                                problematic_id = screenState.value.user[0].problematic.id,
                                email = screenState.value.user[0].email,
                                firstName = screenState.value.user[0].firstName,
                                lastName = screenState.value.user[0].lastName,
                                imageUrl = (if (screenState.value.user[0].images.isNotEmpty()) screenState.value.user[0].images[0].imageName else ""),
                                keyPush = "",//screenState.value.user[0].pushNotifications?.get(0)?.keyPush,
                                role = screenState.value.user[0].roles[0],
                                userName = screenState.value.user[0].username,
                                userToken = screenState.value.token[0].token
                            )
                        )
                    }
                }
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isLoad = false,
                    isNetworkError = false,
                    initCallToken = screenState.value.initCallUser++
                )
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

    fun getSavedToken() = liveData {
        getSavedTokenUseCase.execute().collect {
            emit(it)
            tokenMutable.value = it
        }
    }

    fun getSavedProblematic(userId: Int) = liveData {
        getSavedProblematic.execute(userId).collect {
            emit(it)
            problematic.value = it
        }
    }

    fun deleteUser(user: UserRoom) = viewModelScope.launch {
        deleteSavedUserUseCase.execute(user)
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.IsInitField -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.email,
                    passwordInputValue = event.password
                )
            }
            is AuthEvent.EmailValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.value,
                    userRoom = mutableListOf(),
                    user = mutableListOf(),
                    token = mutableListOf(),
                    tokenRoom = mutableListOf(),
                    currentPage = 1,
                    isLoad = false
                )
            }
            is AuthEvent.PasswordValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    passwordInputValue = event.value,
                    userRoom = mutableListOf(),
                    user = mutableListOf(),
                    token = mutableListOf(),
                    tokenRoom = mutableListOf(),
                    currentPage = 1,
                    isLoad = false
                )
            }
            is AuthEvent.GetToken -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.userName,
                    passwordInputValue = event.password,
                    isLoad = false,
                    currentPage = 1,
                    token = mutableListOf()
                )
                getToken(
                    userName = event.userName,
                    password = event.password
                )
            }
            is AuthEvent.GetSavedProblematic -> {
                viewModelScope.launch {
                    screenState.value.userRoom[0].id?.let {
                        getSavedProblematic.execute(it).collect {
                            _screenState.value = _screenState.value.copy(
                                problematicRoom = mutableListOf(it)
                            )
                        }
                    }
                }
            }
            is AuthEvent.GetUser -> {
                _screenState.value = _screenState.value.copy(
                    emailInputValue = event.userName,
                    isLoad = true,
                    currentPage = 1,
                    user = mutableListOf()
                )
                getUser(
                    userName = event.userName,
                    token = event.token
                )
            }
            is AuthEvent.GetSavedUserByToken -> {
                viewModelScope.launch {
                    getSavedUserUseCase.execute(screenState.value.tokenRoom[0].token).collect {
                        _screenState.value = _screenState.value.copy(
                            userRoom = mutableListOf(it)
                        )
                    }
                }
            }
            is AuthEvent.GetSavedToken -> {
                viewModelScope.launch {
                    getSavedTokenUseCase.execute().collect {
                        _screenState.value = _screenState.value.copy(
                            tokenRoom = mutableListOf(it)
                        )
                    }
                }
            }
            is AuthEvent.InitUserState -> {
                _screenState.value = _screenState.value.copy(
                    isNetworkConnected = true,
                    isNetworkError = false,
                    isLoad = false,
                    currentPage = 1,
                    initCallToken = 0,
                    initCallUser = 0,
                    user = mutableListOf(),
                    userRoom = mutableListOf(),
                    token = mutableListOf(),
                    tokenRoom = mutableListOf(),
                    problematic = mutableListOf(),
                    problematicRoom = mutableListOf(),
                    emailInputValue = "",
                    passwordInputValue = ""
                )
            }
            is AuthEvent.IsNetworkConnected -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.network_error)
                        )
                    )
                }
            }
            is AuthEvent.ConnectionAction -> {
                _screenState.value = _screenState.value.copy(
                    userRoom = mutableListOf(),
                    user = mutableListOf(),
                    token = mutableListOf(),
                    tokenRoom = mutableListOf(),
                    currentPage = 1,
                    isLoad = true
                )
            }
            is AuthEvent.IsNetworkError -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = app.getString(R.string.is_connect_error)
                        )
                    )
                }
            }

            is AuthEvent.IsEmptyField -> {
                if (screenState.value.passwordInputValue.isEmpty()
                    || screenState.value.emailInputValue.isEmpty()
                ) {
                    viewModelScope.launch {
                        _uiEventFlow.emit(
                            UIEvent.ShowMessage(
                                message = getFieldError()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getFieldError(): String {
        return if (screenState.value.passwordInputValue.isEmpty() && screenState.value.passwordInputValue.isEmpty()) {
            app.getString(R.string.is_login_empty_2_field)
        } else if (screenState.value.emailInputValue.isEmpty()) {
            app.getString(R.string.is_login_empty_email_field)
        } else if (screenState.value.passwordInputValue.isEmpty()) {
            app.getString(R.string.is_login_empty_password_field)
        } else {
            ""
        }
    }
}