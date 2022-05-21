package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.UserRepository

class GetTokenUseCase(private val userRepository: UserRepository) {
    suspend fun execute(userName: String, password: String): Resource<ApiTokenResponse> {
        return userRepository.getToken(userName, password)
    }
}