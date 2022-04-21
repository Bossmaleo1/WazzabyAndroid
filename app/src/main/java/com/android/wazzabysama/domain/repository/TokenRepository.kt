package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    //resource for retrofit requests
    suspend fun getToken(): Resource<ApiTokenResponse>

    suspend fun saveToken(token: String)

    suspend fun deleteToken(token: String)

    //Flow for Room Data backup
    fun getSavedToken(): Flow<String>

}