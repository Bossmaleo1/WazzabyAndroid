package com.android.wazzabysama.data.repository.dataSource.publicMessage

import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.Problematic
import retrofit2.Response

interface PublicMessageRemoteDataSource {
    suspend fun getPublicMessage(problematic: Problematic, page: Int, token: String): Response<ApiPublicMessageResponse>
}