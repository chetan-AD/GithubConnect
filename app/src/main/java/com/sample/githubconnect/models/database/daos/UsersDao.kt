package com.sample.githubconnect.models.database.daos

import androidx.room.*
import com.sample.githubconnect.models.entities.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM User where login like :loginName Limit :itemCount Offset :offset")
    suspend fun getUsers(loginName: String, itemCount: Int, offset : Int) : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<User>)
}