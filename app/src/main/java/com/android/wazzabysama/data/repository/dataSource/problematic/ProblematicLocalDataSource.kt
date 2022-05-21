package com.android.wazzabysama.data.repository.dataSource.problematic

import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import kotlinx.coroutines.flow.Flow

interface ProblematicLocalDataSource {
    suspend fun saveProblematicToBD(problematic: ProblematicRoom)
    fun getSavedProblematicForUser(userId: Int): Flow<ProblematicRoom>
}