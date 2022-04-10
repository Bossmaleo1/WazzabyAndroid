package com.android.wazzabysama.domain.usecase

import com.android.wazzabysama.data.model.ApiPublicMessageResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.PublicMessageRepository

class GetPublicMessageUseCase(private val publicMessageRepository: PublicMessageRepository) {
    suspend fun execute(): Resource<ApiPublicMessageResponse> {
        return publicMessageRepository.getPublicMessages()
    }
}