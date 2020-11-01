package com.depromeet.fragraph.core.ext

import com.depromeet.fragraph.data.api.response.ErrorResponse
import com.depromeet.fragraph.domain.model.AppError

fun ErrorResponse.toAppError(): AppError {
    return when (this.code) {
        "G008" -> AppError.ApiCustomizedException.TokenExpiredException(this.message)
        else -> AppError.ApiCustomizedException.UnknownException(this.message)
    }
}