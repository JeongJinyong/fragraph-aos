package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.History
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.model.Memo
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistories(year: Int, month: Int): Flow<List<History>>
    fun saveHistories(keyword: List<Keyword>, incense: Incense, playtime: Int): Flow<Int>
    fun deleteHistory(historyId: Int): Flow<Boolean>

    fun saveMemo(historyId: Int, title: String, contents: String): Flow<Int>
    fun getMemo(memoId: Int): Flow<Memo>
    fun updateMemo(historyId: Int, memo: Memo): Flow<Int>
    fun deleteMemo(historyId: Int, memoId: Int): Flow<Int>
}