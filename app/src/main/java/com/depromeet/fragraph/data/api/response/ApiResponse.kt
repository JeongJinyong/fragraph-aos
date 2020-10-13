package com.depromeet.fragraph.data.api.response

data class ApiResponse<T>(
   val code: String,
   val message: String,
   val data: T?
)