package com.sample.githubconnect.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.repositories.IUserListRepository
import com.sample.githubconnect.util.asLiveData

class UserListViewModel(private val repository: IUserListRepository) : ViewModel() {
    var selectedUser: User? = null
    private var currentQueryValue: String? = null
    private val userListLiveData = MediatorLiveData<PagingData<User>>()
    val userList = userListLiveData.asLiveData()


    suspend fun searchUser(queryString: String) {
        if (queryString == currentQueryValue) {
            return
        }
        currentQueryValue = queryString
        userListLiveData.addSource(
            repository.searchUser(queryString).cachedIn(viewModelScope).asLiveData()
        ) {
            userListLiveData.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}