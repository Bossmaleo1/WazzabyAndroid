package com.android.wazzabysama.presentation.viewModel.publicMessage

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.usecase.publicmessage.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PublicMessageViewModel @Inject constructor(
    private val app: Application,
    private val deleteSavedPublicMessageUseCase: DeleteSavedPublicMessageUseCase,
    private val getPublicMessageUseCase: GetPublicMessageUseCase,
    private val getSavedPublicMessageUseCase: GetSavedPublicMessageUseCase,
    //private val updateSavedPublicMessageUseCase: UpdateSavedPublicMessageUseCase,
    private val savePublicMessageUseCase: SavePublicMessageUseCase
): AndroidViewModel(app) {

    val publicMessageList : MutableLiveData<Resource<ApiPublicMessageResponse>> = MutableLiveData()

    fun getPublicMessage(problematic: Problematic, page: Int, token: String) = viewModelScope.launch(Dispatchers.IO) {
        publicMessageList.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getPublicMessageUseCase.execute(problematic, page, "Bearer "+token)
                publicMessageList.postValue(apiResult)
            } else {
                publicMessageList.postValue(Resource.Error("Internet is available"))
            }
        }catch (e:Exception) {
            publicMessageList.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun savePublicMessageList(publicMessageList: List<PublicMessageRoom>) = viewModelScope.launch {
        publicMessageList.forEach {publicMessageRoom ->
            savePublicMessageUseCase.execute(publicMessageRoom)
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