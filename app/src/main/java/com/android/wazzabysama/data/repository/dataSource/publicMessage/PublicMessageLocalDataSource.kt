package com.android.wazzabysama.data.repository.dataSource.publicMessage

import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import kotlinx.coroutines.flow.Flow

interface PublicMessageLocalDataSource {
    suspend fun savePublicMessageToDB(publicMessage: PublicMessageRoom)
    fun getSavedPublicMessage(publicMessageProblematic: String): Flow<List<PublicMessageRoom>>
    suspend fun deletePublicMessageFromDB(publicMessage: PublicMessageRoom)
}