package com.android.wazzabysama.domain.usecase

import com.android.wazzabysama.data.model.ApiUserResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(): Resource<ApiUserResponse> {
        return userRepository.getUsers()
    }
}