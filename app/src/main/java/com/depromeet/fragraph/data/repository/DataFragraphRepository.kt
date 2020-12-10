package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.core.ext.*
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.data.local.LocalData
import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.*
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
            val titles = listOf(IncenseTitle.LAVENDER, IncenseTitle.PEPPERMINT, IncenseTitle.SANDALWOOD, IncenseTitle.ORANGE, IncenseTitle.EUCALYPTUS)
            val values = listOf(7f, 4f, 8f, 5f, 10f)
            emit(Report(titles, values))
//            fragraphApi.getReports().getBodyOrThrow()?.let { reportsData ->
//                val reportsMap = mutableMapOf(
//                    Pair(IncenseTitle.LAVENDER, 0f),
//                    Pair(IncenseTitle.PEPPERMINT, 0f),
//                    Pair(IncenseTitle.SANDALWOOD, 0f),
//                    Pair(IncenseTitle.ORANGE, 0f),
//                    Pair(IncenseTitle.EUCALYPTUS, 0f),
//                )
//                reportsData.data.values
//                    .map { reportsMap.put(it.name, it.count.toFloat()) }
//                emit(Report(reportsMap.keys.toList(), reportsMap.values.toList()))
//            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getHistories(year: Int, month: Int): Flow<List<History>> {
        return flow {
            val from = getMiliSeconds(year, month, 1).miliSecondsToStringFormat(DF_ISO_8601, LONDON)
            val to = if (month == 12){
                getMiliSeconds(year + 1, 1, 1).miliSecondsToStringFormat(DF_ISO_8601, LONDON)
            } else {
                getMiliSeconds(year, month + 1, 1).miliSecondsToStringFormat(DF_ISO_8601, LONDON)
            }

//            fragraphApi.getHistories(from, to).getBodyOrThrow()?.let {response->
//                response.data.histories.map {
//
//                }
//            }

            val incenses = localData.incenses
            val keywords = mutableListOf<Keyword>()
            keywords.addAll(localData.sandalwoodsKeywords)
            keywords.addAll(localData.peppermintKeywords)
            keywords.addAll(localData.lavenderKeywords)
            keywords.addAll(localData.orangeKeywords)
            keywords.addAll(localData.eucalyptusKeywords)
            val memos = listOf(
                Memo(
                    1,
                    "가나다라마바사아자차카타파하",
                    "기관과 그들의 동력은 위하여 날카로우나 힘있다. 같은 열매를 인간의 품었기 따뜻한 심장의 그들에게 아니다. 이상이 위하여서, 듣기만 하였으며, 꾸며 이상의 싶이 피가 끓는 쓸쓸하랴? 쓸쓸한 간에 무엇이 것이다. 따뜻한 같이 주며, 작고 우리 그들에게 끓는다. 위하여서 내는 살았으며, 피는 위하여, 일월과 피부가 옷을 ",
                    "https://www.emotion.co.kr/wp-content/uploads/2016/07/coding-924920_1920.jpg"
                ),
                Memo(
                    2,
                    "유튜브란 무엇인가",
                    "유튜브(YouTube)는 구글이 서비스하는 동영상 공유 플랫폼이다. 전세계 최대 규모의 동영상 공유 사이트로서, 유튜브 이용자가 영상을 시청·업로드·공유할 수 있다. 유튜브의 본사는 미국 캘리포니아주 샌브루노에 위치해 있다.",
                    null
                ),
                Memo(
                    3,
                    "어떤 영화인데 기억안남",
                    "스티븐 소더버그 감독이 연출한 신종 전염병 유행에 따른 인간의 공포와 사회적 혼란을 그려낸 할리우드 영화.",
                    "https://f1.codingworldnews.com/2019/03/d4gykdrloq.jpg"
                ),
                Memo(
                    4,
                    "이거 영화임",
                    "세계 영화 시장의 상당수를 차지하는 할리우드 덕에 우리는 현대 도시의 이미지를 몇 가지 정도 떠올리곤 한다. 대표적 장소 중 하나인 맨해튼, 그중에서도 타임스퀘어는 현대 문명의 상징이며 시장 자본주의와 민주주의의 성과로 등장하는 시각적 장치물이다. 그곳은 항상 분주하게 활동하는 세계인의 중심 공간이다. 설상 맨해튼의 뉴욕이",
                    null
                ),
                Memo(
                    5,
                    "개발자란 무엇인가",
                    "원래는 모든 분야의 개발에 대해 사용되는 용어다. 그런데 대한민국에서는 개발자라고 하면 주로 '소프트웨어 개발자'를 떠올리곤 한다.",
                    null
                ),
                Memo(
                    6,
                    "기분 나쁨...",
                    "예를 들어, 별 것 아닌 일에 짜증을 내고, 자주 싸우고, 물건을 마구 쇼핑하고, 무분별한 성행위에 탐닉한다던지, 무모한 사업투자 등을 강행해서 결국 본인과 주위사람들에게 큰 손해와 위신을 손상시키는 행동을 하게 되는 등의 문제가 생긴다.",
                    "https://lh3.googleusercontent.com/proxy/uFfXDC-keObiyv2NO4BkBuDF5aObx2UoXk7GJUSEpcPFa3ejcOta0ZAz2DKjvVQkdbyLx91I4XlfRkFeazBzGWr3jXzggqE8VHNoa3Ff1aCVi_sjgDiUuTrZ_EDZmnTB4E-9cTtznYH7cZTTbZWo4FrbcwBer0soSl3jZOGYIDWpAAsLqFH5ZfRCTECdoL5bSsuepjUAbavjTbiuWMdUXazDAc0UoSeEVmtE9CSgUTieUOYLPBnzLCCizh-fZ7xNw0QITwm3NuyarVgsqhVwCH6F1dRfIgtygFNE3hNHkL1aZnv6ScG9Pnfx9z7Pt5ORior9NS5FU9j4TmvxZiFf_Bh5gUbw_K-Wky_bvIRVnQ"
                ),
                Memo(
                    7,
                    "오늘 삼겹살 먹음",
                    "삼겹살 먹어서 기분 짱좋음",
                    "https://avatars3.githubusercontent.com/u/18240792?s=200&v=4"
                ),
            )

            val histories = listOf(
                History(
                    1,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1606795530578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    2,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1606917930578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    3,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607022330578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    4,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607101530578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    5,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607270730578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    6,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607353530578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    7,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607443530578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    8,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607526330578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    9,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607810730578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    10,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607875530578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    11,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1607961930578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    12,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    memos[(Math.random() * 5).toInt()],
                    1608261930578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
                History(
                    13,
                    (Math.random() * 1000).toInt(),
                    incenses[(Math.random() * 5).toInt()],
                    null,
                    1609203930578,
                    listOf(
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()],
                        keywords[(Math.random() * 49).toInt()]
                    )
                ),
            )

            if (month == 12) {
                emit(histories)
            } else {
                emit(listOf<History>())
            }
        }
    }

    override fun saveHistories(keyword: List<Keyword>, incense: Incense, playtime: Int): Flow<Int> {
        return flow {
            emit((Math.random() * 100).toInt())
        }
    }

    override fun deleteHistory(historyId: Int): Flow<Boolean> {
        return flow {
            emit(true)
        }
    }

    override fun saveMemo(historyId: Int, title: String, contents: String): Flow<Int> {
        return flow {

            localData.memoCached = Memo(1, title, contents, null)
            emit(1)
        }
    }

    override fun getMemo(memoId: Int): Flow<Memo> {
        return flow {
            localData.memoCached?.let { emit(it) } ?: kotlin.run {

                // Todo api 에서 memo 를 가져옴
                emit(Memo(1, "abcdefgsasdfdef", "이거 메모 임시임", null))
            }
        }
    }

    override fun updateMemo(historyId: Int, memo: Memo): Flow<Int> {
        return flow {
            localData.memoCached = Memo(memo.id, memo.title, memo.content, null)
            emit(memo.id)
        }
    }

    override fun deleteMemo(historyId: Int, memoId: Int): Flow<Int> {
        return flow {
            localData.memoCached = null
            emit(memoId)
        }
    }

    override fun setMeditation(meditation: Meditation) {
        localData.meditation = meditation
    }

    override fun getMeditation(): Meditation? {
        return localData.meditation
    }
}