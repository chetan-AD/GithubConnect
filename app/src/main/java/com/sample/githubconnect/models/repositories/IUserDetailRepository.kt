package com.sample.githubconnect.models.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sample.githubconnect.models.entities.User
import kotlinx.coroutines.flow.Flow

interface IUserDetailRepository {
    suspend fun getUserDetails(userName: String) : LiveData<User>

    suspend fun getFollowersList(userName: String) : Flow<PagingData<User>>

    suspend fun getFollowingList(userName: String) : Flow<PagingData<User>>

}