package com.sample.githubconnect.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("id")  var id: Long,
    @SerializedName("login") @PrimaryKey var login: String,
    @SerializedName("avatar_url") var imageUrl: String,
    @SerializedName("url") var profileUrl: String?,
    @SerializedName("followers_url") var followersUrl: String?,
    @SerializedName("following_url") var followingUrl: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("bio") var bio: String?
)