package com.sample.githubconnect.models.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.githubconnect.models.RetrofitAPIFactory
import com.sample.githubconnect.models.data.GithubFollowersPagingSource
import com.sample.githubconnect.models.data.GithubFollowingPagingSource
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.response.Resource
import com.sample.githubconnect.models.response.ResponseHandler
import com.sample.githubconnect.models.services.UserServices
import kotlinx.coroutines.flow.Flow

class UserDetailsRepository(private val service: UserServices, private val responseHandler : ResponseHandler) : IUserDetailRepository {

    override suspend fun getUserDetails(userName: String): Resource<User> {
        return try {
            responseHandler.handleSuccess(RetrofitAPIFactory.retrofitAPI().getUserDetails(userName))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getFollowersList(userName: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubFollowersPagingSource(service, userName) }
        ).flow
    }

    override suspend fun getFollowingList(userName: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubFollowingPagingSource(service, userName) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}