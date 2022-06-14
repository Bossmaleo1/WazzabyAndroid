package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.data.api.service.PublicMessageAPIService
import com.android.wazzabysama.data.api.service.UserAPIService
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageRemoteDataSource
import com.android.wazzabysama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.wazzabysama.data.repository.dataSourceImpl.PublicMessageRemoteDataSourceImpl
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
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPIService)
    }

    @Singleton
    @Provides
    fun providePublicMessageRemoteDataSource(
        publicMessageAPIService: PublicMessageAPIService
    ): PublicMessageRemoteDataSource {
        return PublicMessageRemoteDataSourceImpl(publicMessageAPIService)
    }
}