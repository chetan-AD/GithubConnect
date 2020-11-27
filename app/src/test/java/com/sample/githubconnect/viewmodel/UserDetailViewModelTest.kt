package com.sample.githubconnect.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.githubconnect.collectData
import com.sample.githubconnect.getOrAwaitValue
import junit.framework.Assert.assertEquals
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
class UserDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setUp() {
        val fakeUserDetailRepository = FakeUserDetailRepository()
        viewModel = UserDetailViewModel(fakeUserDetailRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testGetUser() = runBlockingTest {
        viewModel.loadUserDetails("user1")

        //Test User Update
        val userDetail = viewModel.user.getOrAwaitValue()
        assertEquals(1, userDetail.data!!.id)

        //Test Following List
        val followingList = viewModel.followingList.getOrAwaitValue()
        assertEquals(2, followingList.collectData().size)

        //Test Follower List
        val followerList = viewModel.followerList.getOrAwaitValue()
        assertEquals(3, followerList.collectData().size)
    }
}