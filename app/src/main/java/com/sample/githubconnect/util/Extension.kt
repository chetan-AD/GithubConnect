package com.sample.githubconnect.util

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sample.githubconnect.R

const val GITHUB_STARTING_PAGE_INDEX = 1
const val RECORD_LIMIT = 10

fun ImageView.loadUrl(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_account_circle_24)
        .error(R.drawable.ic_baseline_account_circle_24)
        .apply(RequestOptions().override(600, 200))
        .into(this)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
