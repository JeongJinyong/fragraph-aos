package com.depromeet.fragraph.data.api.response

data class GetRecommendationData(
    val recommendations: List<RecommendationApiData>
)

data class RecommendationApiData(
    val tags: List<TagsApiData>,
    val incense: IncenseApiData,
    val music: MusicApiData,
    val video: VideoApiData,
)