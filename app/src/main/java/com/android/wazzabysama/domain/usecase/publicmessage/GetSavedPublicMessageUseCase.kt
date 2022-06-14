package com.android.wazzabysama.domain.usecase.publicmessage

import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.domain.repository.PublicMessageRepository
import kotlinx.coroutines.flow.Flow

class GetSavedPublicMessageUseCase(private val publicMessageRepository: PublicMessageRepository) {
    fun execute(): Flow<List<PublicMessageRoom>> {
        return publicMessageRepository.getSavedPublicMessage()
    }
}