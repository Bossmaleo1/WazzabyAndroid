package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.api.ApiCommentResponse
import com.android.wazzabysama.data.model.data.Comment
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    //resource for retrofit requests
    suspend fun getComments(): Resource<ApiCommentResponse>

    suspend fun saveComment(comment: Comment)

    suspend fun deleteComment(comment: Comment)

    //Flow for Room Data backup
    fun getSavedComment(): Flow<List<Comment>>
}