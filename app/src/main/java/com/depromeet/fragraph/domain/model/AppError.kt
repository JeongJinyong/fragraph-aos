package com.depromeet.fragraph.domain.model

sealed class AppError : RuntimeException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)

    sealed class ApiException(cause: Throwable?) : AppError(cause) {
        class NetworkException(cause: Throwable?) : ApiException(cause)
        class ServerException(cause: Throwable?) : ApiException(cause)
        class UnknownException(cause: Throwable?) : AppError(cause)
    }

    sealed class ApiCustomizedException(message: String?): AppError(message) {
        class TokenExpiredException(message: String?) : ApiCustomizedException(message)
        class UnknownException(message: String?) : ApiCustomizedException(message)
    }

    class UnknownException(cause: Throwable?) : AppError(cause)
}
