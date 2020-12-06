package com.depromeet.fragraph.domain.model

data class History (
    val id: Int,
    val playTime: Int,
    val incense: Incense,
    val memo: Memo?,
    val createdAt: Long,
    val keywords: List<Keyword>
)