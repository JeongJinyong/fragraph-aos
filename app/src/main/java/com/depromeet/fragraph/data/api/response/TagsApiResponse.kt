package com.depromeet.fragraph.data.api.response

import com.depromeet.fragraph.domain.model.Category
import com.depromeet.fragraph.domain.model.Keyword

data class GetTagsApiResponse(
    override val code: String,
    override val message: String,
    override val data: GetTagsData
): ApiResponse<GetTagsApiResponse.GetTagsData> {
    data class GetTagsData(
        val tags: List<TagsApiData>,
    )
}

data class TagsApiData(
    val id: Int,
    val name: String,
    val weight: Float,
    val categoryId: Int,
) {
    fun toKeyword(category: Category): Keyword {
        return Keyword(id, name, weight, category)
    }
}