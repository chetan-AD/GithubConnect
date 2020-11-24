package com.sample.githubconnect.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.githubconnect.models.database.daos.UsersDao
import com.sample.githubconnect.models.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}