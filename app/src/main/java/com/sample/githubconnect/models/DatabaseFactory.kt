package com.sample.githubconnect.models

import android.content.Context
import androidx.room.Room
import com.sample.githubconnect.BaseApplication
import com.sample.githubconnect.models.database.AppDatabase

object DatabaseFactory {

    fun getDBInstance(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "GithubUserDb")
            .fallbackToDestructiveMigration()
            .build()
}