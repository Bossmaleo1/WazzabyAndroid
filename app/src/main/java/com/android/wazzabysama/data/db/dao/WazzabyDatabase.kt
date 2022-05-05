package com.android.wazzabysama.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.wazzabysama.data.model.data.*
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom

@Database(
    entities = [
        UserRoom::class,
        ProblematicRoom::class,
        TokenRoom::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WazzabyDatabase : RoomDatabase() {
    abstract fun getUserDAO():UserDAO
    abstract fun getProblematicDAO():ProblematicDAO
}