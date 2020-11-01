package com.depromeet.fragraph.data.api.response

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
    val title: String,
    val detail: String,
)