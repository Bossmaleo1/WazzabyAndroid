package com.android.wazzabysama.domain.usecase.user

import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedUserUseCase(private val userRepository: UserRepository) {
    fun execute(): Flow<List<User>> {
        return userRepository.getSavedUser()
    }
}