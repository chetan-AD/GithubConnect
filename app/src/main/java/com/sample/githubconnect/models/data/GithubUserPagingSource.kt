package com.sample.githubconnect.models.data

import android.util.Log
import androidx.paging.PagingSource
import com.sample.githubconnect.models.database.AppDatabase
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.services.UserServices
import com.sample.githubconnect.util.ConnectionHelper
import com.sample.githubconnect.util.GITHUB_STARTING_PAGE_INDEX
import com.sample.githubconnect.util.RECORD_LIMIT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

class GithubUserPagingSource(
    private val networkService: UserServices,
    databaseService: AppDatabase,
    private val query: String
) : PagingSource<Int, User>(), KoinComponent {
    private val userDao = databaseService.usersDao

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val connectionHelper : ConnectionHelper by inject()
        return try {
            Log.d(
                GithubUserPagingSource::class.java.name,
                "Loading data > $query, position $position, count  = 10"
            )

            var dBResponse : List<User>? = null
            if (!connectionHelper.isOnline()) {
                dBResponse = userDao.getUsers("%$query%", RECORD_LIMIT, position)
            } else {
                val response = networkService.searchUser(query, position, RECORD_LIMIT)
                dBResponse = response.items
                dBResponse.isEmpty().let {
                    GlobalScope.launch {
                        userDao.insertAll(dBResponse)
                    }
                }
                Log.d(
                    GithubUserPagingSource::class.java.name,
                    "Loading from Network " + dBResponse.size
                )
            }

            LoadResult.Page(
                data = dBResponse,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (dBResponse.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
