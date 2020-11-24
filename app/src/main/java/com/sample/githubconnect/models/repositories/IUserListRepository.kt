package com.sample.githubconnect.models.repositories

import androidx.paging.PagingData
import com.sample.githubconnect.models.entities.User
import kotlinx.coroutines.flow.Flow

interface IUserListRepository {
    suspend fun searchUser(userName: String): Flow<PagingData<User>>
}