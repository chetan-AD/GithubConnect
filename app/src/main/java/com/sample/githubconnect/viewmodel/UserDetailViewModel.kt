package com.sample.githubconnect.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.repositories.IUserDetailRepository
import com.sample.githubconnect.models.response.Resource
import com.sample.githubconnect.util.asLiveData
import kotlinx.coroutines.launch

class UserDetailViewModel(private var repository: IUserDetailRepository) : ViewModel() {
    private var lastSearch: String? = ""
    private val userLiveData = MediatorLiveData<Resource<User>>()
    private val followerListLiveData = MediatorLiveData<PagingData<User>>()
    private val followingListLiveData = MediatorLiveData<PagingData<User>>()

    //Expose Readable data for View
    val user = userLiveData.asLiveData()
    val followerList = followerListLiveData.asLiveData()
    val followingList = followingListLiveData.asLiveData()

    fun loadUserDetails(it: String?) {
        if (lastSearch == it) {
            return
        }
        lastSearch = it
        it?.let {
            viewModelScope.launch {
                getUserDetails(it)
                getUserFollowers(it)
                getUserFollowing(it)
            }
        }
    }

    private suspend fun getUserFollowers(userName: String) {
        followerListLiveData.addSource(repository.getFollowersList(userName).cachedIn(viewModelScope).asLiveData()) {
            followerListLiveData.value = it
        }
    }

    private suspend fun getUserDetails(userName: String) {
        userLiveData.value = repository.getUserDetails(userName)
    }

    private suspend fun getUserFollowing(userName: String) {
        followingListLiveData.addSource(repository.getFollowingList(userName).cachedIn(viewModelScope).asLiveData()) {
            followingListLiveData.value = it
        }
    }
}