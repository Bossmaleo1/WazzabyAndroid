package com.android.wazzabysama.domain.usecase.publicmessage

import com.android.wazzabysama.data.model.api.ApiPublicMessageResponse
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.PublicMessageRepository

class GetPublicMessageUseCase(private val publicMessageRepository: PublicMessageRepository) {
    suspend fun execute(problematic: Problematic, page: Int, token: String): Resource<ApiPublicMessageResponse> {
        return publicMessageRepository.getPublicMessages(problematic,page,token)
    }
}