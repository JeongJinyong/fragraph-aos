package com.depromeet.fragraph.data.api.response

import com.depromeet.fragraph.domain.model.Memo

data class PostMemoResponse(
    override val code: String,
    override val message: String,
    override val data: PostMemoData
): ApiResponse<PostMemoResponse.PostMemoData> {
    data class PostMemoData(
        val memoId: Int,
    )
}

data class GetMemoResponse(
    override val code: String,
    override val message: String,
    override val data: GetMemoData
): ApiResponse<GetMemoResponse.GetMemoData> {
    data class GetMemoData(
        val memoId: Int,
        val title: String,
        val detail: String,
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
    val title: String,
    val detail: String,
) {
    fun toMemo(image: String?): Memo {
        return Memo(id, title, detail, image)
    }
}