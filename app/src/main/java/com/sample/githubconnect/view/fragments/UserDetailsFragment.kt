package com.sample.githubconnect.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.sample.githubconnect.R
import com.sample.githubconnect.databinding.FragmentUserDetailsBinding
import com.sample.githubconnect.util.loadUrl
import com.sample.githubconnect.viewmodel.UserDetailViewModel
import com.sample.githubconnect.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : Fragment() {
    private val userListViewModel by sharedViewModel<UserListViewModel>()
    private val userDetailViewModel by viewModel<UserDetailViewModel>()
    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        initViewModel()
        initTabLayout()
        return binding.root
    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        binding.userDetailViewModel = userDetailViewModel
        initSelectedUserDetails()
    }

    private fun initSelectedUserDetails() {
        val selectedUser = userListViewModel.selectedUser
        userDetailViewModel.loadUserDetails(selectedUser?.login)
        binding.imageView.loadUrl(selectedUser?.imageUrl ?: "")
        binding.loginId.text = selectedUser?.login
    }

    private fun initTabLayout() {
        val localNavHost =
            childFragmentManager.findFragmentById(R.id.base_container) as NavHostFragment
        val localController = localNavHost.navController
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(localController.graph.startDestination, false)
            .build()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> localController.navigate(
                        R.id.action_followingListFragment_to_followerListFragment,
                        null,
                        navOptions
                    )
                    1 -> localController.navigate(
                        R.id.action_followerListFragment_to_followingListFragment,
                        null,
                        navOptions
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}