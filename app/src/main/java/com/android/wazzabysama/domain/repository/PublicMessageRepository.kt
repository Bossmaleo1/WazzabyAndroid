package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.PublicMessage
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface PublicMessageRepository {

    suspend fun getPublicMessages(): Resource<ApiPublicMessageResponse>

    suspend fun savePublicMessage(publicMessage: PublicMessage)

    suspend fun deletePublicMessage(publicMessage: PublicMessage)

    fun getSavedPublicMessage(): Flow<List<PublicMessage>>

}