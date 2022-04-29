package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    //resource for retrofit requests
    suspend fun getUsers(userName: String, token: String): Resource<ApiUserResponse>

    suspend fun saveUser(user: User)

    suspend fun deleteUser(user: User)

    //Flow for Room Data backup
    fun getSavedUser():Flow<List<User>>

    //resource for retrofit requests
    suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse>

    suspend fun saveToken(token: String)

    suspend fun deleteToken(token: String)

    //Flow for Room Data backup
    fun getSavedToken(): Flow<String>

}