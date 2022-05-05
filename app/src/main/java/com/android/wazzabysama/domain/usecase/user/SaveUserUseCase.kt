package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SaveUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: UserRoom) = userRepository.saveUser(user)
}