package com.depromeet.fragraph.domain.model

data class Keyword(
    val id : Int,
    val name: String,
    val weight: Float,
    val category: Category,
)