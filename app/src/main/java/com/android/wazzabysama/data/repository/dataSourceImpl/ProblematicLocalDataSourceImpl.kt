package com.android.wazzabysama.data.repository.dataSourceImpl

import com.android.wazzabysama.data.db.dao.ProblematicDAO
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.repository.dataSource.problematic.ProblematicLocalDataSource
import kotlinx.coroutines.flow.Flow

class ProblematicLocalDataSourceImpl(
    private val problematicDAO: ProblematicDAO
): ProblematicLocalDataSource {
    override suspend fun saveProblematicToBD(problematic: ProblematicRoom) {
        problematicDAO.insert(problematic)
    }

    override fun getSavedProblematicForUser(userId: Int): Flow<ProblematicRoom> {
        return problematicDAO.getProblematicForUser(userId)
    }
}