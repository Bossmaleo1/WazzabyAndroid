package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(): Resource<ApiUserResponse> {
        return userRepository.getUsers()
    }
}