package com.android.wazzabysama.data.repository.dataSourceImpl

import com.android.wazzabysama.data.db.dao.PublicMessageDAO
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageLocalDataSource
import kotlinx.coroutines.flow.Flow

class PublicMessageLocalDataSourceImpl(
    private val publicMessageDAO: PublicMessageDAO
) : PublicMessageLocalDataSource {
    override suspend fun savePublicMessageToDB(publicMessage: PublicMessageRoom) {
        publicMessageDAO.insert(publicMessage)
    }

    override fun getSavedPublicMessage(publicMessageProblematic: String): Flow<List<PublicMessageRoom>> {
        return publicMessageDAO.getAllPublicMessages(publicMessageProblematic)
    }

    override suspend fun deletePublicMessageFromDB(publicMessage: PublicMessageRoom) {
        return publicMessageDAO.deletePublicMessage(publicMessage)
    }

    override suspend fun deleTablePublicMessage() {
        return publicMessageDAO.deleteTablePublicMessage()
    }
}