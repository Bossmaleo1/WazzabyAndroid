package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.ApiUserResponse
import com.android.wazzabysama.data.model.PublicMessage
import com.android.wazzabysama.data.model.User
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(): Resource<ApiUserResponse>

    suspend fun saveUser(user: User)

    suspend fun deleteUser(user: User)

    fun getSavedPublicMessage(): Flow<List<PublicMessage>>

}