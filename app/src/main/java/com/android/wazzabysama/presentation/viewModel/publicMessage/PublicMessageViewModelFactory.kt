package com.android.wazzabysama.presentation.viewModel.publicMessage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.wazzabysama.domain.usecase.publicmessage.*

class PublicMessageViewModelFactory(
    private val app: Application,
    private val deleteSavedPublicMessageUseCase: DeleteSavedPublicMessageUseCase,
    private val getPublicMessageUseCase: GetPublicMessageUseCase,
    private val getSavedPublicMessageUseCase: GetSavedPublicMessageUseCase,
    //private val updateSavedPublicMessageUseCase: UpdateSavedPublicMessageUseCase,
    private val savePublicMessageUseCase: SavePublicMessageUseCase
): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PublicMessageViewModel(
            app,
            deleteSavedPublicMessageUseCase,
            getPublicMessageUseCase,
            getSavedPublicMessageUseCase,
            //updateSavedPublicMessageUseCase,
            savePublicMessageUseCase
        ) as T
    }
}