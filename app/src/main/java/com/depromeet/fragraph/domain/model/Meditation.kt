package com.depromeet.fragraph.domain.model

data class Meditation(
    val historyId: Int,
    val playtime: Int,
    val date: Long,
    val incense: Incense,
    val music: Music,
    val video: Video,
)