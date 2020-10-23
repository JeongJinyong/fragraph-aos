package com.depromeet.fragraph.domain.model

data class User (
    val id: Int,
    val nickname: String,
    val profileUrl: String?,
    val provider: String?,
)