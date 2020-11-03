package com.depromeet.fragraph.core.ext

import com.depromeet.fragraph.data.api.response.ErrorResponse
import com.depromeet.fragraph.domain.model.AppError
import kotlinx.coroutines.TimeoutCancellationException

fun ErrorResponse.toAppError(): AppError {
    return when (this.code) {
        "G008" -> AppError.ApiCustomizedException.TokenExpiredException(this.message)
        else -> AppError.ApiCustomizedException.UnknownException(this.message)
    }
}

fun Throwable?.toAppError(): AppError? {
    return when (this) {
        null -> null
        is AppError -> this
        is TimeoutCancellationException -> AppError.ApiException.NetworkException(this)
        else -> AppError.UnknownException(this)
    }
}
