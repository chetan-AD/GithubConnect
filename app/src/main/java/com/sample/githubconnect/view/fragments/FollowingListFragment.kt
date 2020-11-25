package com.sample.githubconnect.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.githubconnect.R
import com.sample.githubconnect.databinding.FragmentFollowingListBinding
import com.sample.githubconnect.databinding.LoadingStateConfigBinding
import com.sample.githubconnect.view.adapters.FollowingListAdapter
import com.sample.githubconnect.viewmodel.UserDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class FollowingListFragment : Fragment() {

    private lateinit var binding: FragmentFollowingListBinding
    private lateinit var loadingStateConfig: LoadingStateConfigBinding

    private val viewModel by lazy {
        parentFragment?.parentFragment?.getViewModel<UserDetailViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_following_list, container, false)
        loadingStateConfig = binding.loadStateLayout
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
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

            userRecyclerViewAdapter.addLoadStateListener {
                when (adapter?.itemCount) {
                    0 -> {
                        when (it.refresh) {
                            is LoadState.Loading -> {
                                showNoRecordsAvailableMessage(loading = true)
                            }
                            is LoadState.Error -> {
                                showNoRecordsAvailableMessage(getString(R.string.try_again))
                            }
                            is LoadState.NotLoading -> {
                                showNoRecordsAvailableMessage(getString(R.string.no_records_found));
                            }
                        }
                    }
                    else -> {
                        showNoRecordsAvailableMessage(showList = true);
                    }
                }
            }
        }
    }

    private fun showNoRecordsAvailableMessage(errorMessage : String = "", loading : Boolean = false, showList : Boolean = false) {
        loadingStateConfig.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (showList) View.VISIBLE else View.GONE
        loadingStateConfig.errorMessage.text = errorMessage
        loadingStateConfig.errorMessage.visibility = if (errorMessage.isEmpty()) View.GONE else View.VISIBLE
    }
}