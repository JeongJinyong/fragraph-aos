package com.depromeet.fragraph.data.api

import com.depromeet.fragraph.data.api.adapter.response.FragraphNetworkResponse
import com.depromeet.fragraph.data.api.request.PostHistoryRequest
import com.depromeet.fragraph.data.api.request.PostMemoRequest
import com.depromeet.fragraph.data.api.request.SocialLoginRequest
import com.depromeet.fragraph.data.api.request.UpdateMyInfoRequest
import com.depromeet.fragraph.data.api.response.*
import retrofit2.http.*

interface FragraphApi {
    @POST("/api/v1/auth/{provider_name}/signin")
    suspend fun loginWithSocial(
        @Path("provider_name") providerName: String,
        @Body request: SocialLoginRequest,
    ): LoginWithSocialApiResponse

    @GET("/api/v1/me")
    suspend fun getMyInfo(): FragraphNetworkResponse<GetMyInfoApiResponse, ErrorResponse>

    @PUT("/api/v1/me")
    suspend fun updateMyInfo(
        @Body request: UpdateMyInfoRequest,
    ): UpdateMyInfoApiResponse

    @GET("/api/v1/tags")
    suspend fun getTags(): GetTagsApiResponse

    @GET("/api/v1/incenses")
    suspend fun getIncenses(): GetIncensesApiResponse

    @GET("/api/v1/recommendations")
    suspend fun getRecommendations(
        @Query("tag") tagIds: List<Int>
    ): GetRecommendationApiResponse

    @GET("/api/v1/reports")
    suspend fun getReports(): FragraphNetworkResponse<GetReportsApiResponse, ErrorResponse>

    @POST("/api/v1/histories")
    suspend fun postHistory(
        @Body request: PostHistoryRequest,
    ): FragraphNetworkResponse<PostHistoryApiResponse, ErrorResponse>

    @GET("/api/v1/histories")
    suspend fun getHistories(
        @Query("from") from: String?,
        @Query("to") to: String?
    ): FragraphNetworkResponse<GetHistoriesApiResponse,ErrorResponse>

    @DELETE("/api/v1/histories/{history_id}")
    suspend fun deleteHistory(
        @Path("history_id") historyId: Int,
    ): DeleteHistoriesApiResponse

    @POST("/api/v1/histories/{history_id}/memos")
    suspend fun postMemo(
        @Path("history_id") historyId: Int,
        @Body request: PostMemoRequest,
    ): PostMemoResponse

    @PUT("/api/v1/histories/{history_id}/memos/{memo_id}")
    suspend fun putMemo(
        @Path("history_id") historyId: Int,
        @Path("memo_id") memoId: Int,
        @Body request: PostMemoRequest,
    ): PutMemoResponse

    @DELETE("/api/v1/histories/{history_id}/memos/{memo_id}")
    suspend fun deleteMemo(
        @Path("history_id") historyId: Int,
        @Path("memo_id") memoId: Int,
    ): DeleteMemoResponse
}