package com.depromeet.fragraph.data.api

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
    ): ApiResponse<SocialLoginData>

    @GET("/api/v1/me")
    suspend fun getMyInfo(): ApiResponse<UserApiData>

    @PUT("/api/v1/me")
    suspend fun updateMyInfo(
        @Body request: UpdateMyInfoRequest,
    ): ApiResponse<UserApiData>

    @GET("/api/v1/tags")
    suspend fun getTags(): ApiResponse<GetTagsData>

    @GET("/api/v1/incenses")
    suspend fun getIncenses(): ApiResponse<GetIncensesData>

    @GET("/api/v1/recommendations")
    suspend fun getIncenses(
        @Query("tag") tagIds: List<Int>
    ): ApiResponse<GetRecommendationData>

    @POST("/api/v1/histories")
    suspend fun postHistory(
        @Body request: PostHistoryRequest,
    ): ApiResponse<PostHistoryData>

    @GET("/api/v1/histories")
    suspend fun getHistories(): ApiResponse<GetHistoriesData>

    @DELETE("/api/v1/histories/{history_id}")
    suspend fun deleteHistory(
        @Path("history_id") historyId: Int,
    ): ApiResponse<Unit>

    @POST("/api/v1/histories/{history_id}/memos")
    suspend fun postMemo(
        @Path("history_id") historyId: Int,
        @Body request: PostMemoRequest,
    ): ApiResponse<PostMemoData>

    @PUT("/api/v1/histories/{history_id}/memos/{memo_id}")
    suspend fun putMemo(
        @Path("history_id") historyId: Int,
        @Path("memo_id") memoId: Int,
        @Body request: PostMemoRequest,
    ): ApiResponse<PostMemoData>

    @DELETE("/api/v1/histories/{history_id}/memos/{memo_id}")
    suspend fun deleteMemo(
        @Path("history_id") historyId: Int,
        @Path("memo_id") memoId: Int,
    ): ApiResponse<Unit>
}