package com.android.wazzabysama.data.repository

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.repository.dataSource.user.UserLocalDataSource
import com.android.wazzabysama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun getUsers(userName: String, token: String): Resource<ApiUserResponse> {
        return responseToResourceUser(userRemoteDataSource.getUser(userName, token))
    }

    private fun responseToResourceUser(response: Response<ApiUserResponse>): Resource<ApiUserResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveUser(user: UserRoom) {
        userLocalDataSource.saveUserToDB(user)
    }

    override suspend fun saveToken(token: TokenRoom) {
        userLocalDataSource.saveTokenToDB(token)
    }

    override suspend fun deleteUser(user: UserRoom) {
        userLocalDataSource.deleteUserFromDB(user)
    }

    override fun getSavedUser(userToken: String): Flow<UserRoom> {
        return userLocalDataSource.getSavedUser(userToken)
    }

    override fun getSavedToken(): Flow<TokenRoom> {
        return userLocalDataSource.getSavedToken()
    }

    override suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse> {
        return responseToResourceToken(userRemoteDataSource.getToken(userName, password))
    }

    private fun responseToResourceToken(response: Response<ApiTokenResponse>): Resource<ApiTokenResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun deleteToken(token: TokenRoom) {
        TODO("Not yet implemented")
    }

}