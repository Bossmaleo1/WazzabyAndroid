package com.android.wazzabysama.data.repository.dataSourceImpl

import com.android.wazzabysama.data.db.dao.UserDAO
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.repository.dataSource.user.UserLocalDataSource
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(
    private val userDAO: UserDAO
) : UserLocalDataSource {
    override suspend fun saveUserToDB(user: UserRoom) {
        userDAO.insert(user)
    }

    override suspend fun saveTokenToDB(token: TokenRoom) {
        userDAO.insertToken(token)
    }

    override fun getSavedToken(): Flow<TokenRoom> {
       return userDAO.getToken()
    }

    override fun getSavedUsers(): Flow<List<UserRoom>> {
        return userDAO.getAllUsers()
    }

    override fun getSavedUser(userToken: String): Flow<UserRoom> {
        return userDAO.getUser(userToken)
    }

    override suspend fun deleteUserFromDB(user: UserRoom) {
        userDAO.deleteUser(user)
    }
}