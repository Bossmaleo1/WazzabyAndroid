package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.domain.repository.UserRepository

class DeleteTableTokenUseCase(private val userRepository: UserRepository) {
    suspend fun execute() = userRepository.deleteTokenTable()
}