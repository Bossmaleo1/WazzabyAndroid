package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.data.repository.ProblematicRepositoryImpl
import com.android.wazzabysama.data.repository.UserRepositoryImpl
import com.android.wazzabysama.data.repository.dataSource.problematic.ProblematicLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.user.UserLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.wazzabysama.domain.repository.ProblematicRepository
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
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideProblematicRepository(
        problematicLocalDataSource: ProblematicLocalDataSource
    ): ProblematicRepository {
        return ProblematicRepositoryImpl(problematicLocalDataSource)
    }
}