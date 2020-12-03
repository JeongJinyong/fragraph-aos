package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.History
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistories(month: String): Flow<List<History>>
    fun saveHistories(keyword: List<Keyword>, incense: Incense, playtime: Int): Flow<Int>
}