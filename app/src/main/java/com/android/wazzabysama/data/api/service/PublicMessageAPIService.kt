package com.android.wazzabysama.data.api.service

import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PublicMessageAPIService {
    @GET("/api/public_messages")
    suspend fun getPublicMessage(
        @Query("problematic")
        problematic: String,
        @Query("_page")
        page: Int,
        @Query("pagination")
        pagination: Boolean,
        @Header("Authorization")
        token: String
    ): Response<ApiPublicMessageResponse>
}