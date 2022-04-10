package com.android.wazzabysama.domain.usecase

import com.android.wazzabysama.data.model.ApiCommentResponse
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.CommentRepository

class GetCommentUseCase(private val commentRepository: CommentRepository) {

    suspend fun execute(): Resource<ApiCommentResponse> {
        return commentRepository.getComments()
    }
}