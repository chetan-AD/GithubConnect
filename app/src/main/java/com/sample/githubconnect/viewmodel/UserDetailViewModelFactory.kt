package com.sample.githubconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.githubconnect.models.repositories.IUserDetailRepository

class UserDetailViewModelFactory(private val repository: IUserDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailViewModel(repository) as T
    }
}