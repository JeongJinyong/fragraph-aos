package com.depromeet.fragraph.core.ext

import com.depromeet.fragraph.data.api.adapter.response.FragraphNetworkResponse
import com.depromeet.fragraph.data.api.response.ErrorResponse
import com.depromeet.fragraph.domain.model.AppError

fun <T: Any> FragraphNetworkResponse<T, ErrorResponse>.getBodyOrThrow(): T? {
    return when (this) {
        is FragraphNetworkResponse.Success -> this.body
        is FragraphNetworkResponse.ApiError -> throw this.body.toAppError()
        is FragraphNetworkResponse.NetworkError -> throw AppError.ApiException.NetworkException(this.error)
        is FragraphNetworkResponse.UnknownError -> throw AppError.ApiException.UnknownException(this.error)
    }
}