package com.sample.githubconnect.view.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.githubconnect.R
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.util.loadUrl

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val loginId: TextView = view.findViewById(R.id.login_id)
    private val description: TextView = view.findViewById(R.id.description)
    private val image: ImageView = view.findViewById(R.id.profile_image)

    fun bind(item: User, onItemClick: (User) -> Unit){
        loginId.text = item.login
        description.text = item.bio
        image.loadUrl(item.imageUrl)
        view.setOnClickListener { onItemClick(item) }
    }
    override fun toString(): String {
        return super.toString() + " '" + description.text + "'"
    }
}