package com.android.wazzabysama.presentation.di

import android.app.Application
import com.android.wazzabysama.domain.usecase.user.GetTokenUseCase
import com.android.wazzabysama.domain.usecase.user.GetUserUseCase
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
        getTokenUseCase: GetTokenUseCase
    ): UserViewModelFactory{
        return UserViewModelFactory(
            application,
            getUserUseCase,
            getTokenUseCase
        )
    }
}