package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.data.repository.UserRepositoryImpl
import com.android.wazzabysama.data.repository.dataSource.UserRemoteDataSource
import com.android.wazzabysama.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }
}