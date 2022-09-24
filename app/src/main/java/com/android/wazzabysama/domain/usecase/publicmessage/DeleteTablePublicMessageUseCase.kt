package com.android.wazzabysama.domain.usecase.publicmessage

import com.android.wazzabysama.domain.repository.PublicMessageRepository

class DeleteTablePublicMessageUseCase(private val publicMessageRepository: PublicMessageRepository) {
    suspend fun execute() = publicMessageRepository.deletePublicMessageTable()
}