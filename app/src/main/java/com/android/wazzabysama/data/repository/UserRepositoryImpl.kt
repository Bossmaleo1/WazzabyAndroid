package com.android.wazzabysama.data.repository

import com.android.wazzabysama.data.model.api.ApiUserResponse
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.repository.dataSource.UserRemoteDataSource
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun getUsers(): Resource<ApiUserResponse> {
        return responseToResource(userRemoteDataSource.getUser())
    }

    private fun responseToResource(response: Response<ApiUserResponse>): Resource<ApiUserResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result->
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
}