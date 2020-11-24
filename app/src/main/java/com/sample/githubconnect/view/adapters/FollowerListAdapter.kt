package com.sample.githubconnect.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sample.githubconnect.R
import com.sample.githubconnect.models.entities.User

class FollowerListAdapter(
    private val onItemClick: (User) -> Unit
) : PagingDataAdapter<User, ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onItemClick)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<User>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldUser: User,
                                         newUser: User) = oldUser.id == newUser.id

            override fun areContentsTheSame(oldUser: User,
                                            newUser: User) = oldUser == newUser
        }
    }
}