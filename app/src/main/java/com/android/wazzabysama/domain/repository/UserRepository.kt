package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    //resource for retrofit requests
    suspend fun getUsers(userName: String, token: String): Resource<ApiUserResponse>

    suspend fun saveUser(user: UserRoom)

    suspend fun deleteUser(user: UserRoom)

    //Flow for Room Data backup
    fun getSavedUser(userToken: String):Flow<UserRoom>

    //resource for retrofit requests
    suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse>

    suspend fun saveToken(token: TokenRoom)

    suspend fun deleteToken(token: TokenRoom)

    //Flow for Room Data backup
    fun getSavedToken(): Flow<TokenRoom>

}