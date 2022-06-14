package com.android.wazzabysama.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PublicMessageViewModelFactory(
    private val app: Application
): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PublicMessageViewModel(
            app
        ) as T
    }
}