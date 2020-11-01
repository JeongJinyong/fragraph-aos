package com.depromeet.fragraph.data.api.response

data class LoginWithSocialApiResponse(
    override val code: String,
    override val message: String,
    override val data: SocialLoginData
) :ApiResponse<SocialLoginData>

data class SocialLoginData(
    val jwt: String,
    val userCreated: Boolean,
)