package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedTokenUseCase(private val userRepository: UserRepository) {
    fun execute(): Flow<TokenRoom> {
        return userRepository.getSavedToken()
    }
}