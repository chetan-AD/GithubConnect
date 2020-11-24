package com.sample.githubconnect.models.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.githubconnect.models.RetrofitAPIFactory
import com.sample.githubconnect.models.data.GithubFollowersPagingSource
import com.sample.githubconnect.models.data.GithubFollowingPagingSource
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.services.UserServices
import kotlinx.coroutines.flow.Flow

class UserDetailsRepository(private val service: UserServices,) : IUserDetailRepository {
    private var user: MutableLiveData<User> = MutableLiveData()

    override suspend fun getUserDetails(userName: String): LiveData<User> {
        val response = RetrofitAPIFactory.retrofitAPI().getUserDetails(userName)
        if (response.isSuccessful) {
            user.value = response.body()
        } else {
            user.value = null
        }
        return user
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