package com.android.wazzabysama.presentation.viewModel.camera

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CameraViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(app) as T
    }
}