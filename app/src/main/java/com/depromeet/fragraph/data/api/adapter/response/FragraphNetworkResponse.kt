package com.depromeet.fragraph.data.api.adapter.response

import java.io.IOException

sealed class FragraphNetworkResponse<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T?) : FragraphNetworkResponse<T, Nothing>()
    data class ApiError<U : Any>(val body: U, val code: Int) : FragraphNetworkResponse<Nothing, U>()
    data class NetworkError(val error: IOException) : FragraphNetworkResponse<Nothing, Nothing>()
    data class UnknownError(val error: Throwable) : FragraphNetworkResponse<Nothing, Nothing>()
}