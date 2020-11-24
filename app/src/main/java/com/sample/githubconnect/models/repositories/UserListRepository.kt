package com.sample.githubconnect.models.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.githubconnect.models.data.GithubUserPagingSource
import com.sample.githubconnect.models.database.AppDatabase
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.services.UserServices
import kotlinx.coroutines.flow.Flow


class UserListRepository(private val networkService: UserServices,
                         private val databaseService: AppDatabase) : IUserListRepository {

    override suspend fun searchUser(userName: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                GithubUserPagingSource(networkService, databaseService, userName)
            }
        ).flow
    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}