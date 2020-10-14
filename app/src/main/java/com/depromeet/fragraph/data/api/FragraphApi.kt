package com.depromeet.fragraph.data.api

import com.depromeet.fragraph.data.api.request.SocialLoginRequest
import com.depromeet.fragraph.data.api.request.UpdateMyInfoRequest
import com.depromeet.fragraph.data.api.response.ApiResponse
import com.depromeet.fragraph.data.api.response.GetUserData
import com.depromeet.fragraph.data.api.response.SocialLoginData
import retrofit2.http.*

interface FragraphApi {
    @POST("/auth/{provider_name}/signin")
    suspend fun loginWithSocial(
        @Path("provider_name") providerName: String,
        @Body request: SocialLoginRequest,
    ): ApiResponse<SocialLoginData>

    @GET("/me")
    suspend fun getMyInfo(): ApiResponse<GetUserData>

    @PUT("/me")
    suspend fun updateMyInfo(
        @Body request: UpdateMyInfoRequest,
    ): ApiResponse<GetUserData>
}