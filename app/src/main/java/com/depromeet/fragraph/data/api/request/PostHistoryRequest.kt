package com.depromeet.fragraph.data.api.request

data class PostHistoryRequest(
    val tagIds: List<Int>,
    val incenseId: Int,
    val playTime: Int,
)