package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedUserUseCase(private val userRepository: UserRepository) {
    fun execute(userToken: String): Flow<UserRoom> {
        return userRepository.getSavedUser(userToken)
    }
}