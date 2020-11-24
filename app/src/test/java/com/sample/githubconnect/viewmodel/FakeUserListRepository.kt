package com.sample.githubconnect.viewmodel

import androidx.paging.PagingData
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.repositories.IUserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserListRepository : IUserListRepository {

    private val userList = mutableListOf<User>()

    init {
        userList.add(User(
            1,
            "user1",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1",
            "",
            ""
        ))
        userList.add(User(
            2,
            "user2",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1111",
            "https://ex.com/1",
            "",
            ""
        ))
        userList.add(User(
            3,
            "user11",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1111",
            "https://ex.com/1",
            "",
            ""
        ))
        userList.add(User(
            4,
            "user16",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1",
            "",
            ""
        ))
        userList.add(User(
            5,
            "user26",
            "https://ex.com/1",
            "https://ex.com/1",
            "https://ex.com/1111",
            "https://ex.com/1",
            "",
            ""
        ))
    }

    override suspend fun searchUser(userName: String): Flow<PagingData<User>> {
        return flowOf(PagingData.from(userList).filterSync { it.login.contains(userName)})
    }
}