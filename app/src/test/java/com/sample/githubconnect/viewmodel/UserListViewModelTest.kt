package com.sample.githubconnect.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.githubconnect.collectData
import com.sample.githubconnect.getOrAwaitValue
import com.sample.githubconnect.models.entities.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class UserListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserListViewModel


    @Before
    fun setUp() {
        val fakeUserListRepository = FakeUserListRepository()
        viewModel = UserListViewModel(fakeUserListRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testSearchUser() = runBlockingTest {
        viewModel.searchUser("user16")
        var data : PagingData<User> = viewModel.userList.getOrAwaitValue()
        assertEquals(1, data.collectData().size)
        assertEquals("user16", data.collectData()[0].login)

        viewModel.searchUser("user")
        data = viewModel.userList.getOrAwaitValue()
        assertEquals(5, data.collectData().size)

        viewModel.searchUser("null")
        data = viewModel.userList.getOrAwaitValue()
        assertEquals(0, data.collectData().size)
    }
}

