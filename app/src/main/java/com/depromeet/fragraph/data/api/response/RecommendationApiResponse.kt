package com.depromeet.fragraph.data.api.response

data class GetRecommendationApiResponse(
    override val code: String,
    override val message: String,
    override val data: GetRecommendationData
): ApiResponse<GetRecommendationApiResponse.GetRecommendationData> {
    data class GetRecommendationData(
        val recommendations: List<RecommendationApiData>
    )
}

data class RecommendationApiData(
    val tags: List<TagsApiData>,
    val incense: IncenseApiData,
    val music: MusicApiResponse,
    val video: VideoApiResponse,
)