package com.depromeet.fragraph.data.api.response

data class GetIncensesData(
    val incenses: List<IncenseApiData>,
)

data class IncenseApiData(
    val id: Int,
    val title: String,
    val detail: String,
)