package com.depromeet.fragraph.data.api

import com.depromeet.fragraph.data.api.request.SocialLoginRequest
import com.depromeet.fragraph.data.api.response.ApiResponse
import com.depromeet.fragraph.data.api.response.SocialLoginData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface FragraphApi {
    @POST("/auth/{provider_name}/signin")
    suspend fun login(
        @Path("provider_name") providerName: String,
        @Body request: SocialLoginRequest
    ): ApiResponse<SocialLoginData>
}