package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.domain.repository.UserRepository

class DeleteTableUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute() = userRepository.deleteUserTable()
}