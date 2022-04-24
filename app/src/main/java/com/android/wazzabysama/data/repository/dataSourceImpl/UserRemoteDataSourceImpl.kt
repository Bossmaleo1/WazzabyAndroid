package com.android.wazzabysama.data.repository.dataSourceImpl

import com.android.wazzabysama.data.api.UserAPIService
import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.repository.dataSource.UserRemoteDataSource
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val userAPIService: UserAPIService,
    private val userName: String,
    private val password: String
):  UserRemoteDataSource {
    override suspend fun getToken(): Response<ApiTokenResponse> {
        return userAPIService.getToken(userName = userName, password = password)
    }

    override suspend fun getUser(): Response<ApiUserResponse> {
        TODO("Not yet implemented")
    }

}