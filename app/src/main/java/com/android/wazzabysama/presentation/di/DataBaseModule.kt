package com.android.wazzabysama.presentation.di

import android.app.Application
import androidx.room.Room
import com.android.wazzabysama.data.db.dao.ProblematicDAO
import com.android.wazzabysama.data.db.dao.UserDAO
import com.android.wazzabysama.data.db.dao.WazzabyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideUserDatabase(app: Application): WazzabyDatabase {
        return Room.databaseBuilder(app,WazzabyDatabase::class.java,"wazzaby_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(wazzabyDatabase: WazzabyDatabase): UserDAO {
        return wazzabyDatabase.getUserDAO()
    }

    @Singleton
    @Provides
    fun provideProblematicDao(wazzabyDatabase: WazzabyDatabase): ProblematicDAO {
        return wazzabyDatabase.getProblematicDAO()
    }
}