package com.depromeet.fragraph.data.api.response

data class GetMyInfoApiResponse(
    override val code: String,
    override val message: String,
    override val data: UserApiData
) : ApiResponse<UserApiData>

data class UpdateMyInfoApiResponse(
    override val code: String,
    override val message: String,
    override val data: UserApiData
) : ApiResponse<UserApiData>

data class UserApiData(
    val id: Int,
    val nickname: String,
    val profileUrl: String?,
    val provider: String,
)