package com.android.wazzabysama.presentation.di

import com.android.wazzabysama.data.db.dao.ProblematicDAO
import com.android.wazzabysama.data.db.dao.PublicMessageDAO
import com.android.wazzabysama.data.db.dao.UserDAO
import com.android.wazzabysama.data.repository.dataSource.problematic.ProblematicLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.user.UserLocalDataSource
import com.android.wazzabysama.data.repository.dataSourceImpl.ProblematicLocalDataSourceImpl
import com.android.wazzabysama.data.repository.dataSourceImpl.PublicMessageLocalDataSourceImpl
import com.android.wazzabysama.data.repository.dataSourceImpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(userDAO: UserDAO): UserLocalDataSource {
        return UserLocalDataSourceImpl(userDAO)
    }

    @Singleton
    @Provides
    fun provideProblematicLocalDataSource(problematicDAO: ProblematicDAO): ProblematicLocalDataSource {
        return ProblematicLocalDataSourceImpl(problematicDAO)
    }

    @Singleton
    @Provides
    fun providePublicMessageLocalDataSource(publicMessageDAO: PublicMessageDAO): PublicMessageLocalDataSource {
        return PublicMessageLocalDataSourceImpl(publicMessageDAO)
    }
}