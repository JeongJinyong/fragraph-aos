package com.depromeet.fragraph.domain.model

data class History (
    val id: Int,
    val day: Long,
    val playTime: Int,
    val incense: Incense,
    val memo: Memo,
)