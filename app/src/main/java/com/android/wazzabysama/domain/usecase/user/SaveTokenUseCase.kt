package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.domain.repository.UserRepository

class SaveTokenUseCase(private val userRepository: UserRepository) {
    suspend fun execute(token: TokenRoom) = userRepository.saveToken(token)
}