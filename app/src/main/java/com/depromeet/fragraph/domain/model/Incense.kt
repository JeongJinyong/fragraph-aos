package com.depromeet.fragraph.domain.model

import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class Incense(
    val id: Int,
    val title: IncenseTitle,
    val content: String,
    val image: String,
    val category: Category,
)