package com.android.wazzabysama.data.repository

import com.android.wazzabysama.data.model.api.ApiTokenResponse
import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.repository.dataSource.UserRemoteDataSource
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
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

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun getSavedUser(): Flow<List<User>> {
        TODO("Not yet implemented")
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

    override suspend fun saveToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun getSavedToken(): Flow<String> {
        TODO("Not yet implemented")
    }
}