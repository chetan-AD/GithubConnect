package com.sample.githubconnect.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.githubconnect.R
import com.sample.githubconnect.databinding.FragmentUserListBinding
import com.sample.githubconnect.databinding.LoadingStateConfigBinding
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.view.adapters.UserRecyclerViewAdapter
import com.sample.githubconnect.viewmodel.UserListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A fragment representing a list of Items.
 */
class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private lateinit var loadingStateConfig: LoadingStateConfigBinding

    private val viewModel: UserListViewModel by sharedViewModel<UserListViewModel>()
    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        loadingStateConfig = binding.loadStateLayout
        initViewModel()
        initSearchView()
        initRecyclerView()
        showNoRecordsAvailableMessage(getString(R.string.no_records_available), loading = false, showList = false)
        return binding.root
    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel.userList.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                userRecyclerViewAdapter.submitData(it)
            }
        })
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                if (text.isNullOrEmpty()) {
                    Toast.makeText(context, "Please enter text", Toast.LENGTH_LONG).show()
                } else {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        viewModel.searchUser(text)
                    }
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }

        })
    }

    private fun initRecyclerView() {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            userRecyclerViewAdapter = UserRecyclerViewAdapter() { user: User ->
                onItemClickListener(user)
            }

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
            adapter = userRecyclerViewAdapter
        }
    }

    private fun showNoRecordsAvailableMessage(errorMessage : String = "", loading : Boolean = false, showList : Boolean = false) {
        loadingStateConfig.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.list.visibility = if (showList) View.VISIBLE else View.GONE
        loadingStateConfig.errorMessage.text = errorMessage
        loadingStateConfig.errorMessage.visibility = if (errorMessage.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun onItemClickListener(item: User) {
        viewModel.selectedUser = item
        findNavController(this).navigate(R.id.action_userListFragment_to_userDetailsFragment)
    }
}