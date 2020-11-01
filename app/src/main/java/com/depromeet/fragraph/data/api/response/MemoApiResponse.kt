package com.depromeet.fragraph.data.api.response

data class PostMemoResponse(
    override val code: String,
    override val message: String,
    override val data: PostMemoData
): ApiResponse<PostMemoResponse.PostMemoData> {
    data class PostMemoData(
        val memoId: Int,
    )
}

data class PutMemoResponse(
    override val code: String,
    override val message: String,
    override val data: PutMemoData
): ApiResponse<PutMemoResponse.PutMemoData> {
    data class PutMemoData(
        val memoId: Int,
    )
}

data class DeleteMemoResponse(
    override val code: String,
    override val message: String,
    override val data: Unit
): ApiResponse<Unit>

data class MemoApiData(
    val id: Int,
    val detail: String,
)