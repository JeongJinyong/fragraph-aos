package com.depromeet.fragraph.data.api.response

interface ApiResponse<T> {
   val code: String
   val message: String
   val data: T?
}

data class ErrorResponse(
   override val code: String,
   override val message: String,
   override val data: Unit?
) : ApiResponse<Unit?>