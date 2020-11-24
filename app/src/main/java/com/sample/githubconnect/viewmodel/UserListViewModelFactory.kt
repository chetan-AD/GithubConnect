package com.sample.githubconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.githubconnect.models.repositories.IUserListRepository

class UserListViewModelFactory(private val repository: IUserListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(repository) as T
    }
}