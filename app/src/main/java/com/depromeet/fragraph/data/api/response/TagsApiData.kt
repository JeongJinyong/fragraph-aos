package com.depromeet.fragraph.data.api.response

data class GetTagsData(
    val tags: List<TagsApiData>,
)

data class TagsApiData(
    val id: Int,
    val name: String,
    val weight: Float,
)