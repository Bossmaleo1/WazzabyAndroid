package com.android.wazzabysama.data.repository.dataSource.user

import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun saveUserToDB(user: UserRoom)
    suspend fun saveTokenToDB(token: TokenRoom)
    fun getSavedToken(): Flow<TokenRoom>
    fun getSavedUsers(): Flow<List<UserRoom>>
    fun getSavedUser(userToken: String): Flow<UserRoom>
    suspend fun deleteUserFromDB(user: UserRoom)
    suspend fun deleteUserTable()
    suspend fun deleteTokenTable()
}