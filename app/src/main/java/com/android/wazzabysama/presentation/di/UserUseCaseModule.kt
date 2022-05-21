package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.domain.repository.ProblematicRepository
import com.android.wazzabysama.domain.repository.UserRepository
import com.android.wazzabysama.domain.usecase.problematic.GetSavedProblematicUseCase
import com.android.wazzabysama.domain.usecase.problematic.SaveProblematicUseCase
import com.android.wazzabysama.domain.usecase.user.*
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

    @Singleton
    @Provides
    fun provideSaveUserUseCase(
        userRepository: UserRepository
    ): SaveUserUseCase {
        return SaveUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideSaveTokenUseCase(
        userRepository: UserRepository
    ): SaveTokenUseCase {
        return SaveTokenUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideSaveProblematicUseCase(
        problematicRepository: ProblematicRepository
    ): SaveProblematicUseCase {
        return SaveProblematicUseCase(problematicRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedUserUseCase(
        userRepository: UserRepository
    ): GetSavedUserUseCase {
        return GetSavedUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedProblematicUseCase(
        problematicRepository: ProblematicRepository
    ): GetSavedProblematicUseCase {
        return GetSavedProblematicUseCase(problematicRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedTokenUseCase(
        userRepository: UserRepository
    ): GetSavedTokenUseCase {
        return GetSavedTokenUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedUserUseCase(
        userRepository: UserRepository
    ): DeleteSavedUserUseCase {
        return DeleteSavedUserUseCase(userRepository)
    }

}