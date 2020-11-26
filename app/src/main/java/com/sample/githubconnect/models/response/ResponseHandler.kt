package com.sample.githubconnect.models.response

import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(ErrorCodes.SOCKET_TIMEOUT.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SOCKET_TIMEOUT.code -> "Timeout"
            ErrorCodes.UNAUTHORIZED.code -> "Unauthorised"
            ErrorCodes.NOT_FOUND.code -> "API Not found"
            else -> "Something went wrong"
        }
    }
}