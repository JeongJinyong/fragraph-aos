package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistories(month: String): Flow<List<History>>
}