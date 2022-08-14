package com.android.wazzabysama.data.repository.dataSourceImpl

import android.util.Log
import com.android.wazzabysama.data.api.service.PublicMessageAPIService
import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageRemoteDataSource
import retrofit2.Response


class PublicMessageRemoteDataSourceImpl(
    private val publicMessageAPIService: PublicMessageAPIService
) : PublicMessageRemoteDataSource {
    override suspend fun getPublicMessage(
        problematic: Problematic,
        page: Int,
        token: String
    ): Response<ApiPublicMessageResponse> {
        return publicMessageAPIService.getPublicMessage(
            "/api/problematics/${problematic.id}",
            page,
            true,
            token
        )
    }
}