package com.android.wazzabysama.presentation.di

import android.app.Application
import com.android.wazzabysama.domain.usecase.problematic.GetSavedProblematicUseCase
import com.android.wazzabysama.domain.usecase.problematic.SaveProblematicUseCase
import com.android.wazzabysama.domain.usecase.user.*
import com.android.wazzabysama.presentation.viewModel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        application: Application,
        getUserUseCase: GetUserUseCase,
        getTokenUseCase: GetTokenUseCase,
        saveUserUseCase: SaveUserUseCase,
        saveProblematicUseCase: SaveProblematicUseCase,
        saveTokenUseCase: SaveTokenUseCase,
        getSavedUserUseCase: GetSavedUserUseCase,
        getSavedTokenUseCase: GetSavedTokenUseCase,
        getSavedProblematic: GetSavedProblematicUseCase,
        deleteSavedUserUseCase: DeleteSavedUserUseCase
    ): UserViewModelFactory{
        return UserViewModelFactory(
            application,
            getUserUseCase,
            getTokenUseCase,
            saveUserUseCase,
            saveProblematicUseCase,
            saveTokenUseCase,
            getSavedUserUseCase,
            getSavedTokenUseCase,
            getSavedProblematic,
            deleteSavedUserUseCase
        )
    }
}