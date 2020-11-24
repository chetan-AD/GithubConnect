package com.sample.githubconnect.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.githubconnect.R
import com.sample.githubconnect.databinding.FragmentFollowingListBinding
import com.sample.githubconnect.view.adapters.FollowingListAdapter
import com.sample.githubconnect.viewmodel.UserDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class FollowingListFragment : Fragment() {

    private lateinit var binding: FragmentFollowingListBinding
    private val viewModel by lazy {
        parentFragment?.parentFragment?.getViewModel<UserDetailViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_following_list, container, false)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            val userRecyclerViewAdapter = FollowingListAdapter() {
            }
            adapter = userRecyclerViewAdapter
            viewModel?.followingList?.observe(viewLifecycleOwner, {
                lifecycleScope.launch {
                    userRecyclerViewAdapter.submitData(it)
                }
            })
        }
        return binding.root
    }
}