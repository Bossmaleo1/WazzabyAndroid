package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.domain.repository.UserRepository

class DeleteSavedUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: UserRoom) = userRepository.deleteUser(user)
}