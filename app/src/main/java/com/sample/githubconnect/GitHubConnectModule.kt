package com.sample.githubconnect

import com.sample.githubconnect.models.RetrofitAPIFactory
import com.sample.githubconnect.models.DatabaseFactory
import com.sample.githubconnect.models.repositories.IUserDetailRepository
import com.sample.githubconnect.models.repositories.IUserListRepository
import com.sample.githubconnect.models.repositories.UserDetailsRepository
import com.sample.githubconnect.models.repositories.UserListRepository
import com.sample.githubconnect.util.ConnectionHelper
import com.sample.githubconnect.viewmodel.UserDetailViewModel
import com.sample.githubconnect.viewmodel.UserDetailViewModelFactory
import com.sample.githubconnect.viewmodel.UserListViewModel
import com.sample.githubconnect.viewmodel.UserListViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gitHubModuleInfo = module {
    single { RetrofitAPIFactory.retrofitAPI() }
    single { DatabaseFactory.getDBInstance(androidContext()) }

    single<IUserListRepository> { UserListRepository(get(), get()) }
    single { UserListViewModelFactory(get()) }
    single<IUserDetailRepository> { UserDetailsRepository(get()) }
    single { UserDetailViewModelFactory(get()) }
    single { RetrofitAPIFactory }
    single { DatabaseFactory }
    single { ConnectionHelper(androidContext()) }

    viewModel { UserListViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }

}