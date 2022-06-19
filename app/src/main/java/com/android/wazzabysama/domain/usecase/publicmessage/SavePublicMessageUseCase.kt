package com.android.wazzabysama.domain.usecase.publicmessage

import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.domain.repository.PublicMessageRepository

class SavePublicMessageUseCase(private val publicMessageRepository: PublicMessageRepository) {
    suspend fun execute(publicMessage: PublicMessageRoom) = publicMessageRepository.savePublicMessage(publicMessage)
}