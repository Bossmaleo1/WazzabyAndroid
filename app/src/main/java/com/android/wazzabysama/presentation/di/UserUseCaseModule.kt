package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.domain.repository.UserRepository
import com.android.wazzabysama.domain.usecase.user.GetTokenUseCase
import com.android.wazzabysama.domain.usecase.user.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserUseCaseModule {
    @Singleton
    @Provides
    fun provideTokenUseCase(
        userRepository: UserRepository
    ): GetTokenUseCase {
        return GetTokenUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }
}