package com.android.wazzabysama.data.api

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPIService {
    @POST("/api/login_check")
    suspend fun getToken(
        @Query("username")
        userName: String,
        @Query("password")
        password: String
    ): Response<ApiTokenResponse>

    @GET("/api/users")
    suspend fun getUser(
        @Query("username")
        userName: String
    ): Response<ApiUserResponse>

}