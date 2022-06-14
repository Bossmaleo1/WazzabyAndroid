package com.android.wazzabysama.data.repository

import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageRemoteDataSource
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.PublicMessageRepository
import kotlinx.coroutines.flow.Flow

class PublicMessageRepositoryImpl(
    private val publicMessageRemoteDataSource: PublicMessageRemoteDataSource,
    private val publicMessageLocalDataSource : PublicMessageLocalDataSource
) : PublicMessageRepository {
    override suspend fun getPublicMessages(): Resource<ApiPublicMessageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun savePublicMessage(publicMessage: PublicMessage) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePublicMessage(publicMessage: PublicMessage) {
        TODO("Not yet implemented")
    }

    override fun getSavedPublicMessage(): Flow<List<PublicMessageRoom>> {
        TODO("Not yet implemented")
    }
}