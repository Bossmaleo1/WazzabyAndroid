package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface PublicMessageRepository {

    //resource for retrofit requests
    suspend fun getPublicMessages(): Resource<ApiPublicMessageResponse>

    suspend fun savePublicMessage(publicMessage: PublicMessage)

    suspend fun deletePublicMessage(publicMessage: PublicMessage)

    //Flow for Room Data backup
    fun getSavedPublicMessage(): Flow<List<PublicMessage>>

}