
package com.sample.githubconnect.models.data

import androidx.paging.PagingSource
import com.sample.githubconnect.models.entities.User
import com.sample.githubconnect.models.services.UserServices
import com.sample.githubconnect.util.GITHUB_STARTING_PAGE_INDEX
import com.sample.githubconnect.util.RECORD_LIMIT
import retrofit2.HttpException
import java.io.IOException

class GithubFollowingPagingSource(
    private val service: UserServices,
    private val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response = service.getFollowingList(query, position, RECORD_LIMIT)
            LoadResult.Page(
                    data = response,
                    prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
