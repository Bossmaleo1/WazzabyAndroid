package com.android.wazzabysama.data.repository.dataSourceImpl

import com.android.wazzabysama.data.api.UserAPIService
import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.repository.dataSource.UserRemoteDataSource
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val userAPIService: UserAPIService
):  UserRemoteDataSource {
    override suspend fun getToken(userName: String, password: String): Response<ApiTokenResponse> {
        return userAPIService.getToken(userName, password)
    }

    override suspend fun getUser(userName: String): Response<ApiUserResponse> {
        return userAPIService.getUser(userName)
    }

}