package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.domain.repository.PublicMessageRepository
import com.android.wazzabysama.domain.repository.UserRepository
import com.android.wazzabysama.domain.usecase.publicmessage.DeleteSavedPublicMessageUseCase
import com.android.wazzabysama.domain.usecase.publicmessage.GetPublicMessageUseCase
import com.android.wazzabysama.domain.usecase.publicmessage.GetSavedPublicMessageUseCase
import com.android.wazzabysama.domain.usecase.publicmessage.SavePublicMessageUseCase
import com.android.wazzabysama.domain.usecase.user.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PublicMessageUseCaseModule {
    @Singleton
    @Provides
    fun providePublicMessageUseCase(
        publicMessageRepository: PublicMessageRepository
    ): GetPublicMessageUseCase {
        return GetPublicMessageUseCase(publicMessageRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedPublicMessageUseCase(
        publicMessageRepository: PublicMessageRepository
    ): GetSavedPublicMessageUseCase {
        return GetSavedPublicMessageUseCase(publicMessageRepository)
    }

    @Singleton
    @Provides
    fun provideSavePublicMessageUseCase(
        publicMessageRepository: PublicMessageRepository
    ): SavePublicMessageUseCase {
        return SavePublicMessageUseCase(publicMessageRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedPublicMessageUseCase(
        publicMessageRepository: PublicMessageRepository
    ): DeleteSavedPublicMessageUseCase {
        return DeleteSavedPublicMessageUseCase(publicMessageRepository)
    }
}