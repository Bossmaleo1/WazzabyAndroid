package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    //resource for retrofit requests
    suspend fun getUsers(): Resource<ApiUserResponse>

    suspend fun saveUser(user: User)

    suspend fun deleteUser(user: User)

    //Flow for Room Data backup
    fun getSavedUser(): Flow<List<User>>

}