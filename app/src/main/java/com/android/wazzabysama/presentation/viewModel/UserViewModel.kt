package com.android.wazzabysama.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.usecase.user.GetTokenUseCase
import com.android.wazzabysama.domain.usecase.user.GetUserUseCase
import com.android.wazzabysama.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val app: Application,
    private val getUserUseCase: GetUserUseCase,
    private val getTokenUseCase: GetTokenUseCase
): AndroidViewModel(app) {

    val token : MutableLiveData<Resource<ApiTokenResponse>> = MutableLiveData()
    val user : MutableLiveData<Resource<ApiUserResponse>> = MutableLiveData()
    private val statusMessageError = MutableLiveData<Event<String>>()
    val messageError : LiveData<Event<String>>
        get() = statusMessageError

    init {
        statusMessageError.value = Event("NetWork Error")
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