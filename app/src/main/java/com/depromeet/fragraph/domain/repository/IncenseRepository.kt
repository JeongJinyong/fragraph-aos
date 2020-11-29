package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.model.Recommendation
import kotlinx.coroutines.flow.Flow

interface IncenseRepository {
    fun getIncenses(): Flow<List<Incense>>
    fun getRecommendationIncenses(): Flow<List<Recommendation>>
}