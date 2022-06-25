package com.android.wazzabysama.presentation.viewModel.user

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.Token
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.usecase.problematic.GetSavedProblematicUseCase
import com.android.wazzabysama.domain.usecase.problematic.SaveProblematicUseCase
import com.android.wazzabysama.domain.usecase.user.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
): AndroidViewModel(app) {

    //val tokenState: MutableState<List<TokenRoom>> = mutableStateOf(emptyList<TokenRoom>())
    val token : MutableLiveData<Resource<ApiTokenResponse>> = MutableLiveData()
    val user : MutableLiveData<Resource<ApiUserResponse>> = MutableLiveData()

    private val tokenMutable: MutableLiveData<TokenRoom> = MutableLiveData()
    val tokenValue: LiveData<TokenRoom> = tokenMutable

    private val userMutable: MutableLiveData<UserRoom> = MutableLiveData()
    val userValue: LiveData<UserRoom> = userMutable

    private val problematic: MutableLiveData<ProblematicRoom> =  MutableLiveData()
    val problematicValue: LiveData<ProblematicRoom> = problematic


    init {
        //tokenState.value = listOf(this.getSavedToken().value) as List<TokenRoom>
    }

    fun getToken(userName: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        token.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getTokenUseCase.execute(userName, password)
                token.postValue(apiResult)
            } else {
                token.postValue(Resource.Error("Internet is available"))
            }
        }catch (e:Exception) {
            token.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getUser(userName: String, token: String) = viewModelScope.launch(Dispatchers.IO) {
        user.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getUserUseCase.execute(userName, token)
                user.postValue(apiResult)
            } else {
                user.postValue(Resource.Error("Internet is available"))
            }
        }catch (e:Exception) {
            user.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun saveUser(user: UserRoom) = viewModelScope.launch {
        saveUserUseCase.execute(user)
    }

    fun saveProblematic(problematic: ProblematicRoom) = viewModelScope.launch {
        saveProblematicUseCase.execute(problematic)
    }

    fun saveToken(token: TokenRoom) = viewModelScope.launch {
        saveTokenUseCase.execute(token)
    }

    fun getSavedUserByToken(userToken: String) = liveData {
        getSavedUserUseCase.execute(userToken).collect {
            emit(it)
            userMutable.value = it
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
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
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
}