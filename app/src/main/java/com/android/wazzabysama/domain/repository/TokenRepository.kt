package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.ApiTokenResponse
import com.android.wazzabysama.data.util.Resource

interface TokenRepository {

    suspend fun getToken(): Resource<ApiTokenResponse>

}