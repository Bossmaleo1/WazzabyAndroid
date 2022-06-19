package com.android.wazzabysama.data.repository

import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageRemoteDataSource
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.PublicMessageRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PublicMessageRepositoryImpl(
    private val publicMessageRemoteDataSource: PublicMessageRemoteDataSource,
    private val publicMessageLocalDataSource : PublicMessageLocalDataSource
) : PublicMessageRepository {

    override suspend fun getPublicMessages(problematic: Problematic, page: Int,token: String): Resource<ApiPublicMessageResponse> {
        return responseToResourcePublicMessage(publicMessageRemoteDataSource.getPublicMessage(problematic,  page, token))
    }

    override suspend fun savePublicMessage(publicMessage: PublicMessageRoom) {
        publicMessageLocalDataSource.savePublicMessageToDB(publicMessage)
    }

    private fun responseToResourcePublicMessage(response: Response<ApiPublicMessageResponse>): Resource<ApiPublicMessageResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    override suspend fun deletePublicMessage(publicMessage: PublicMessage) {
        TODO("Not yet implemented")
    }

    override fun getSavedPublicMessage(publicMessageProblematic: String): Flow<List<PublicMessageRoom>> {
        return publicMessageLocalDataSource.getSavedPublicMessage(publicMessageProblematic)
    }
}