package com.android.wazzabysama.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.wazzabysama.domain.usecase.problematic.GetSavedProblematicUseCase
import com.android.wazzabysama.domain.usecase.problematic.SaveProblematicUseCase
import com.android.wazzabysama.domain.usecase.user.*

class UserViewModelFactory(
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
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        return UserViewModel(
            app,
            getUserUseCase,
            getTokenUseCase,
            saveUserUseCase,
            saveProblematicUseCase,
            saveTokenUseCase,
            getSavedUserUseCase,
            getSavedTokenUseCase,
            getSavedProblematic,
            deleteSavedUserUseCase
        ) as T
    }
}