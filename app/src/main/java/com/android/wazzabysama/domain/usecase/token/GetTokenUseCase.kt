package com.android.wazzabysama.domain.usecase.token

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.TokenRepository

class GetTokenUseCase(private val tokenUserRepository: TokenRepository) {
    suspend fun execute(): Resource<ApiTokenResponse> {
        return tokenUserRepository.getToken()
    }
}