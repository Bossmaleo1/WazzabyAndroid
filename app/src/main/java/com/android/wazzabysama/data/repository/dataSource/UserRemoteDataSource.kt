package com.android.wazzabysama.data.repository.dataSource

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getToken(): Response<ApiTokenResponse>
    suspend fun getUser(): Response<ApiUserResponse>
}