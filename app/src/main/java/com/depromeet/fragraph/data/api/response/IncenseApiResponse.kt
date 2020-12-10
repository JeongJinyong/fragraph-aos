package com.depromeet.fragraph.data.api.response

import com.depromeet.fragraph.domain.model.Category
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class GetIncensesApiResponse(
    override val code: String,
    override val message: String,
    override val data: GetIncensesData
) : ApiResponse<GetIncensesApiResponse.GetIncensesData> {

    data class GetIncensesData(
        val incenses: List<IncenseApiData>,
    )
}

data class IncenseApiData(
    val id: Int,
    val name: IncenseTitle,
    val image: String,
    val detail: String,
    val categoryId: Int,
) {
    fun toIncense(category: Category): Incense {
        return Incense(id, name, detail, image, category)
    }
}