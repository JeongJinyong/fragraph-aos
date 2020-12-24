package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.core.ext.*
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.data.api.request.PostHistoryRequest
import com.depromeet.fragraph.data.api.request.PostMemoRequest
import com.depromeet.fragraph.data.local.LocalData
import com.depromeet.fragraph.data.local.getDummyHistories
import com.depromeet.fragraph.data.local.getDummyReports
import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class DataFragraphRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fragraphApi: FragraphApi,
    private val localData: LocalData,
) : IncenseRepository, KeywordRepository, ReportRepository, HistoryRepository,
    MeditationRepository {

    override fun getIncenses(): Flow<List<Incense>> {
        return flow {
            emit(localData.incenses)
        }
    }

    override fun getRecommendationIncenses(): Flow<List<Recommendation>> {
        return flow {
            // Todo 추후 Api 연동 필요
            val recommendations = localData.selectedKeywords.groupBy {
                it.category.id
            }.map {
                val index =
                    localData.incenses.indexOfFirst { incense -> it.key == incense.category.id }
                val incense = localData.incenses[index]
                val music = localData.musics[index]
                val video = localData.videos[index]
                Recommendation(incense, it.value, music, video)
            }
            emit(recommendations)
        }
    }

    override fun getRandomKeywords(): Flow<List<Keyword>> {
        return flow {
            // Todo 추후에 api 연동 필요
            val keywords = mutableListOf<Keyword>()
            keywords.addAll(localData.sandalwoodsKeywords.shuffled().subList(0, 5))
            keywords.addAll(localData.peppermintKeywords.shuffled().subList(0, 5))
            keywords.addAll(localData.lavenderKeywords.shuffled().subList(0, 5))
            keywords.addAll(localData.orangeKeywords.shuffled().subList(0, 5))
            keywords.addAll(localData.eucalyptusKeywords.shuffled().subList(0, 5))
            emit(keywords.shuffled())
        }
    }

    override fun saveSelectKeywords(keywords: List<Keyword>) {
        localData.selectedKeywords.clear()
        localData.selectedKeywords.addAll(keywords)
    }

    override fun getReport(): Flow<Report> {
        return flow {
//            emit(getDummyReports())
            fragraphApi.getReports().getBodyOrThrow()?.let { reportsData ->
                val reportsMap = mutableMapOf(
                    Pair(IncenseTitle.LAVENDER, 0f),
                    Pair(IncenseTitle.PEPPERMINT, 0f),
                    Pair(IncenseTitle.SANDALWOOD, 0f),
                    Pair(IncenseTitle.ORANGE, 0f),
                    Pair(IncenseTitle.EUCALYPTUS, 0f),
                )
                reportsData.data.values
                    .map { reportsMap.put(it.name, it.count.toFloat()) }
                emit(Report(reportsMap.keys.toList(), reportsMap.values.toList()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getHistories(year: Int, month: Int): Flow<List<History>> {
        return flow {
//            emit(getDummyHistories())
            val from = getMilliSeconds(year, month, 1).miliSecondsToStringFormat(DF_ISO_8601, LONDON)
            val to = if (month == 12){
                getMilliSeconds(year + 1, 1, 1).miliSecondsToStringFormat(DF_ISO_8601, LONDON)
            } else {
                getMilliSeconds(year, month + 1, 1).miliSecondsToStringFormat(DF_ISO_8601, LONDON)
            }

            fragraphApi.getHistories(from, to).getBodyOrThrow()?.let {response->
                val histories = response.data.histories.map {historyApi ->
                    val category = localData.categories.firstOrNull { it.id == historyApi.incense.categoryId } ?: Category(-1, "알수 없음")
                    History(
                        historyApi.id,
                        historyApi.playTime,
                        historyApi.incense.toIncense(category),
                        if(historyApi.memos.isNotEmpty()) historyApi.memos[0].toMemo(null) else null,
                        historyApi.createdAt.toMilliseconds(DF_SIMPLE_ISO_8601, LONDON),
                        historyApi.tags.map { it.toKeyword(category) }
                    )
                }
                emit(histories)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun saveHistories(keyword: List<Keyword>, incense: Incense, playtime: Int): Flow<Int> {
        return flow {
            fragraphApi.postHistory(
                PostHistoryRequest(localData.selectedKeywords.map { it.id }, incense.id, playtime)
            ).getBodyOrThrow()?.let { response ->
                emit(response.data.historyId)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun deleteHistory(historyId: Int): Flow<Boolean> {
        return flow {
            fragraphApi.deleteHistory(historyId).getBodyOrThrow()?.let { response ->
                emit(true)
            }
        }
    }

    override fun saveMemo(historyId: Int, title: String, contents: String): Flow<Int> {
        return flow {
            fragraphApi.postMemo(historyId, PostMemoRequest(title, contents))
                .getBodyOrThrow()?.let { response ->
                    localData.memoCached = Memo(response.data.memoId, title, contents, null)
                    emit(response.data.memoId)
                }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMemo(historyId: Int, memoId: Int): Flow<Memo> {
        return flow {
            localData.memoCached?.let { emit(it) } ?: kotlin.run {
                fragraphApi.getMemo(historyId, memoId).getBodyOrThrow()?.let {response ->
                    val memo = Memo(
                        response.data.memoId,
                        response.data.title,
                        response.data.detail,
                        null,
                    )
                    localData.memoCached = memo
                    emit(memo)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun updateMemo(historyId: Int, memo: Memo): Flow<Int> {
        return flow {
            fragraphApi.putMemo(historyId, memo.id, PostMemoRequest(memo.title, memo.content)
            ).getBodyOrThrow()?.let {response ->
                localData.memoCached = Memo(response.data.memoId, memo.title, memo.content, null)
                emit(response.data.memoId)
            }
        }
    }

    override fun deleteMemo(historyId: Int, memoId: Int): Flow<Int> {
        return flow {
            fragraphApi.deleteMemo(historyId, memoId).getBodyOrThrow()?.let {response ->
                localData.memoCached = null
                emit(memoId)
            }
        }
    }

    override fun setMeditation(meditation: Meditation) {
        localData.meditation = meditation
    }

    override fun getMeditation(): Meditation? {
        return localData.meditation
    }
}