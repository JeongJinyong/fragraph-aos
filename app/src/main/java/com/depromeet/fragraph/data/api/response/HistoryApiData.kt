package com.depromeet.fragraph.data.api.response

data class PostHistoryData(
    val historyId: Int,
)

data class GetHistoriesData(
    val lastId: Int,
    val histories: List<HistoryApiData>,
)

data class HistoryApiData(
    val id: Int,
    val createdAt: String,
    val playTime: Int,
    val incense: IncenseApiData,
    val memos: List<MemoApiData>,
)