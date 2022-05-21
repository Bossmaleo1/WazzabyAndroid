package com.android.wazzabysama.data.repository

import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.repository.dataSource.problematic.ProblematicLocalDataSource
import com.android.wazzabysama.domain.repository.ProblematicRepository
import kotlinx.coroutines.flow.Flow

class ProblematicRepositoryImpl(
    private val problematicLocalDataSource: ProblematicLocalDataSource
) : ProblematicRepository {
    override fun getSavedProblematic(userId: Int): Flow<ProblematicRoom> {
        return problematicLocalDataSource.getSavedProblematicForUser(userId)
    }

    override suspend fun saveProblematic(problematic: ProblematicRoom) {
        problematicLocalDataSource.saveProblematicToBD(problematic)
    }
}