package com.depromeet.fragraph.data.api.response

data class MusicApiResponse(
    val id: Int,
    val nickname: String,
    val profileUrl: String?,
    val provider: String,
)