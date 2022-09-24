package com.android.wazzabysama.presentation.viewModel.drop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.wazzabysama.domain.usecase.publicmessage.DeleteTablePublicMessageUseCase
import com.android.wazzabysama.domain.usecase.user.DeleteTableTokenUseCase
import com.android.wazzabysama.domain.usecase.user.DeleteTableUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DropViewModel @Inject constructor(
    private val app: Application,
    private val deleteTableUserUseCase: DeleteTableUserUseCase,
    private val deleteTableTokenUseCase: DeleteTableTokenUseCase,
    private val deleteTablePublicMessageUseCase: DeleteTablePublicMessageUseCase
): AndroidViewModel(app) {
    init {

    }

    fun deleteAll() = viewModelScope.launch {
        deleteTableTokenUseCase.execute()
        deleteTableUserUseCase.execute()
        deleteTablePublicMessageUseCase.execute()
    }
}