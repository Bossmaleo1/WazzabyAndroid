package com.android.wazzabysama.data.repository.dataSourceImpl

import com.android.wazzabysama.data.api.service.PublicMessageAPIService
import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.repository.dataSource.publicMessage.PublicMessageRemoteDataSource
import retrofit2.Response


class PublicMessageRemoteDataSourceImpl(
    private val publicMessageAPIService: PublicMessageAPIService
): PublicMessageRemoteDataSource {
    override suspend fun getPublicMessage(
        problematic: Problematic,
        page: Int
    ): Response<ApiPublicMessageResponse> {
        return publicMessageAPIService.getPublicMessage(problematic.wording,page,true)
    }
}