package com.depromeet.fragraph.feature.recommendation.incense_select.model

import com.depromeet.fragraph.domain.model.Category
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.model.Music
import com.depromeet.fragraph.domain.model.Video

data class IncenseItemUiModel(
    val id: Int,
    val title: String,
    val content: String,
    val image: String,
    val category: Category,
    val video: Video,
    val music: Music,
    val keywords: List<Keyword>,
)