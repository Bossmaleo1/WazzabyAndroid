package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.data.api.service.UserAPIService
import com.android.wazzabysama.data.repository.dataSource.UserRemoteDataSource
import com.android.wazzabysama.data.repository.dataSourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        userAPIService: UserAPIService
    ):UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPIService)
    }
}