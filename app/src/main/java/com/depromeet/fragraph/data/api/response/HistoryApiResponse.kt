package com.depromeet.fragraph.data.api.response

import com.depromeet.fragraph.domain.model.Incense

data class PostHistoryApiResponse(
    override val code: String,
    override val message: String,
    override val data: PostHistoryData
) : ApiResponse<PostHistoryApiResponse.PostHistoryData> {
    data class PostHistoryData(
        val historyId: Int,
    )
}

data class GetHistoriesApiResponse(
    override val code: String,
    override val message: String,
    override val data: GetHistoriesData
) : ApiResponse<GetHistoriesApiResponse.GetHistoriesData> {

    data class GetHistoriesData(
        val histories: List<HistoryApiData>,
    )
}

data class DeleteHistoriesApiResponse(
    override val code: String,
    override val message: String,
    override val data: Unit
) :ApiResponse<Unit>

data class HistoryApiData(
    val id: Int,
    val createdAt: String,
    val playTime: Int,
    val incense: IncenseApiData,
    val memos: List<MemoApiData>,
    val tags: List<TagsApiData>,
)