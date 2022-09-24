package com.android.wazzabysama.presentation.viewModel.drop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.wazzabysama.domain.usecase.publicmessage.DeleteTablePublicMessageUseCase
import com.android.wazzabysama.domain.usecase.user.DeleteTableTokenUseCase
import com.android.wazzabysama.domain.usecase.user.DeleteTableUserUseCase

class DropViewModelFactory(
    private val app: Application,
    private val deleteTableUserUseCase: DeleteTableUserUseCase,
    private val deleteTableTokenUseCase: DeleteTableTokenUseCase,
    private val deleteTablePublicMessageUseCase: DeleteTablePublicMessageUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DropViewModel(
            app,
            deleteTableUserUseCase,
            deleteTableTokenUseCase,
            deleteTablePublicMessageUseCase
        ) as T
    }
}