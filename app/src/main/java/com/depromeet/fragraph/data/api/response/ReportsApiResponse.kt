package com.depromeet.fragraph.data.api.response

import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class GetReportsApiResponse(
    override val code: String,
    override val message: String,
    override val `data`: Map<String, ReportsResult>
) : ApiResponse<Map<String, GetReportsApiResponse.ReportsResult>> {

    data class ReportsResult(
        val name: IncenseTitle,
        val count: Int,
    )
}