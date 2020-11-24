package com.sample.githubconnect.models.services

import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.entities.UserListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserServices {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") userName: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): UserListModel

    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") userName: String): Response<User>

    @GET("users/{username}/followers")
    suspend fun getFollowersList(
        @Path("username") userName: String, @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<User>

    @GET("users/{username}/following")
    suspend fun getFollowingList(
        @Path("username") userName: String, @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<User>
}