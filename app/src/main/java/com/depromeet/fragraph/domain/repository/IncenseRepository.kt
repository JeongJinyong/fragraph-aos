package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.Incense
import kotlinx.coroutines.flow.Flow

interface IncenseRepository {
    fun getIncenses(): Flow<List<Incense>>
}