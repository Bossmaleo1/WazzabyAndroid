package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.ApiCommentResponse
import com.android.wazzabysama.data.model.Comment
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    suspend fun getComments(): Resource<ApiCommentResponse>

    suspend fun saveComment(comment: Comment)

    suspend fun deleteComment(comment: Comment)

    fun getSavedComment(): Flow<List<Comment>>
}