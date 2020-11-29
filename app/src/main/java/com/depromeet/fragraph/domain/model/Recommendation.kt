package com.depromeet.fragraph.domain.model

data class Recommendation(
    val incense: Incense,
    val keyword: List<Keyword>,
    val music: Music,
    val video: Video,
)