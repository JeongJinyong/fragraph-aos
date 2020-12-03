package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.HistoryRepository
import com.depromeet.fragraph.domain.repository.IncenseRepository
import com.depromeet.fragraph.domain.repository.KeywordRepository
import com.depromeet.fragraph.domain.repository.ReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataFragraphRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fragraphApi: FragraphApi,
) : IncenseRepository, KeywordRepository, ReportRepository, HistoryRepository {

    val selectedKeywords = mutableListOf<Keyword>()

    override fun getIncenses(): Flow<List<Incense>> {
        return flow {
            fragraphApi.getIncenses().data?.let {incenseData ->
                val incenses = incenseData.incenses
                    .map { Incense(it.id, it.title, it.detail, "") }
                emit(incenses)
            }
        }
    }

    override fun getRecommendationIncenses(): Flow<List<Recommendation>> {
        return flow {

        }
    }

    override fun getRandomKeywords(): Flow<List<Keyword>> {
        return flow{
            emit(keywords.shuffled().subList(0, 21))
        }
    }

    override fun saveSelectKeywords(keywords: List<Keyword>) {
        selectedKeywords.clear()
        selectedKeywords.addAll(keywords)
    }

    override fun getReport(): Flow<Report> {
        return flow {
            val titles = listOf(IncenseTitle.LAVENDER, IncenseTitle.PEPPERMINT, IncenseTitle.SANDALWOOD, IncenseTitle.ORANGE, IncenseTitle.EUCALYPTUS)
            val values = listOf(7f, 4f, 8f, 5f, 10f)
            emit(Report(titles, values))
        }
    }

    override fun getHistories(month: String): Flow<List<History>> {
        return flow {

            val incenses = listOf(
                Incense(1, IncenseTitle.LAVENDER, "어쩌구 저쩌구", "이미지라네"),
                Incense(2, IncenseTitle.PEPPERMINT, "어쩌구 저쩌구", "이미지라네"),
                Incense(3, IncenseTitle.SANDALWOOD, "어쩌구 저쩌구", "이미지라네"),
                Incense(4, IncenseTitle.ORANGE, "어쩌구 저쩌구", "이미지라네"),
                Incense(5, IncenseTitle.EUCALYPTUS, "어쩌구 저쩌구", "이미지라네"),
            )
            val memos = listOf(
                Memo(1, "가나다라마바사아자차카타파하", "기관과 그들의 동력은 위하여 날카로우나 힘있다. 같은 열매를 인간의 품었기 따뜻한 심장의 그들에게 아니다. 이상이 위하여서, 듣기만 하였으며, 꾸며 이상의 싶이 피가 끓는 쓸쓸하랴? 쓸쓸한 간에 무엇이 것이다. 따뜻한 같이 주며, 작고 우리 그들에게 끓는다. 위하여서 내는 살았으며, 피는 위하여, 일월과 피부가 옷을 ", "https://www.emotion.co.kr/wp-content/uploads/2016/07/coding-924920_1920.jpg"),
                Memo(2, "유튜브란 무엇인가","유튜브(YouTube)는 구글이 서비스하는 동영상 공유 플랫폼이다. 전세계 최대 규모의 동영상 공유 사이트로서, 유튜브 이용자가 영상을 시청·업로드·공유할 수 있다. 유튜브의 본사는 미국 캘리포니아주 샌브루노에 위치해 있다.", null),
                Memo(3, "어떤 영화인데 기억안남","스티븐 소더버그 감독이 연출한 신종 전염병 유행에 따른 인간의 공포와 사회적 혼란을 그려낸 할리우드 영화.", "https://f1.codingworldnews.com/2019/03/d4gykdrloq.jpg"),
                Memo(4, "이거 영화임","세계 영화 시장의 상당수를 차지하는 할리우드 덕에 우리는 현대 도시의 이미지를 몇 가지 정도 떠올리곤 한다. 대표적 장소 중 하나인 맨해튼, 그중에서도 타임스퀘어는 현대 문명의 상징이며 시장 자본주의와 민주주의의 성과로 등장하는 시각적 장치물이다. 그곳은 항상 분주하게 활동하는 세계인의 중심 공간이다. 설상 맨해튼의 뉴욕이", null),
                Memo(5, "개발자란 무엇인가","원래는 모든 분야의 개발에 대해 사용되는 용어다. 그런데 대한민국에서는 개발자라고 하면 주로 '소프트웨어 개발자'를 떠올리곤 한다.", null),
                Memo(6, "기분 나쁨...","예를 들어, 별 것 아닌 일에 짜증을 내고, 자주 싸우고, 물건을 마구 쇼핑하고, 무분별한 성행위에 탐닉한다던지, 무모한 사업투자 등을 강행해서 결국 본인과 주위사람들에게 큰 손해와 위신을 손상시키는 행동을 하게 되는 등의 문제가 생긴다.", "https://lh3.googleusercontent.com/proxy/uFfXDC-keObiyv2NO4BkBuDF5aObx2UoXk7GJUSEpcPFa3ejcOta0ZAz2DKjvVQkdbyLx91I4XlfRkFeazBzGWr3jXzggqE8VHNoa3Ff1aCVi_sjgDiUuTrZ_EDZmnTB4E-9cTtznYH7cZTTbZWo4FrbcwBer0soSl3jZOGYIDWpAAsLqFH5ZfRCTECdoL5bSsuepjUAbavjTbiuWMdUXazDAc0UoSeEVmtE9CSgUTieUOYLPBnzLCCizh-fZ7xNw0QITwm3NuyarVgsqhVwCH6F1dRfIgtygFNE3hNHkL1aZnv6ScG9Pnfx9z7Pt5ORior9NS5FU9j4TmvxZiFf_Bh5gUbw_K-Wky_bvIRVnQ"),
                Memo(7, "오늘 삼겹살 먹음","삼겹살 먹어서 기분 짱좋음", "https://avatars3.githubusercontent.com/u/18240792?s=200&v=4"),
            )

            val histories = listOf(
                History(1, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1606795530578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(2, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1606917930578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(3, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607022330578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(4, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607101530578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(5, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607270730578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(6, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607353530578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(7, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607443530578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(8, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607526330578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(9, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607810730578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(10, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607875530578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(11, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1607961930578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
                History(12, (Math.random() * 1000).toInt(), incenses[(Math.random() * 5).toInt()], memos[(Math.random() * 5).toInt()], 1609203930578, listOf(keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()], keywords[(Math.random() * 49).toInt()])),
            )

            emit(histories)
        }
    }

    companion object {
        val keywords = listOf(
            Keyword(1, "잠 못드는 밤", 0.3f),
            Keyword(2, "우울해", 0.4f),
            Keyword(3, "잠이 안와", 0.8f),
            Keyword(4, "힘든 하루", 0.2f),
            Keyword(5, "스트레스", 0.5f),
            Keyword(6, "마음대로 되지 않는 하루", 0.3f),
            Keyword(7, "멀리 떠나고 싶은", 0.4f),
            Keyword(8, "토닥토닥", 0.1f),
            Keyword(9, "머리가 복잡해", 0.6f),
            Keyword(10, "집중이 필요한", 0.8f),
            Keyword(11, "상쾌해", 0.2f),
            Keyword(12, "불안불안", 0.3f),
            Keyword(13, "망치면 어떡하지", 0.2f),
            Keyword(14, "상쾌해 지고싶은", 0.5f),
            Keyword(15, "민초단", 0.6f),
            Keyword(16, "주변이 왜 이렇게 시끄럽지?", 0.1f),
            Keyword(17, "아직 할 일이 남은", 0.4f),
            Keyword(18, "공부할 타이밍", 0.6f),
            Keyword(19, "우울해", 0.7f),
            Keyword(20, "사랑하고 싶어", 0.6f),
            Keyword(21, "머리가 지끈거리는", 0.5f),
            Keyword(22, "목이 칼칼해", 0.4f),
            Keyword(23, "혈압 상승!!", 0.3f),
            Keyword(24, "분노조절장애", 0.5f),
            Keyword(25, "머리가 띵!", 0.6f),
            Keyword(26, "고단한", 0.7f),
            Keyword(27, "허탈한", 0.8f),
            Keyword(28, "으슬으슬", 0.2f),
            Keyword(29, "솟아라 엔돌핀이여!", 0.3f),
            Keyword(30, "정신적 스트레스", 0.4f),
            Keyword(31, "활기찬 하루", 0.6f),
            Keyword(32, "신나는", 0.7f),
            Keyword(33, "기운없는", 0.8f),
            Keyword(34, "지친 하루 끝에서", 0.8f),
            Keyword(35, "시체가 되고 싶어", 0.3f),
            Keyword(36, "아무것도 하기 싫어", 0.8f),
            Keyword(37, "번아웃 증후군", 0.5f),
            Keyword(38, "기분전환이 필요해", 0.7f),
            Keyword(39, "하늘을 날아갈 것 같은", 0.3f),
            Keyword(40, "설레는", 0.1f),
            Keyword(41, "웃음이 나오는", 0.7f),
            Keyword(42, "색다른 것을 하고 싶은", 0.6f),
            Keyword(43, "안정이 필요한", 0.8f),
            Keyword(44, "꿀꿀한", 0.3f),
            Keyword(45, "하루의 마무리", 0.5f),
            Keyword(46, "마음이 뒤숭숭해", 0.1f),
            Keyword(47, "생각이 생각을 불러오는", 0.4f),
            Keyword(48, "너무 신이나", 0.3f),
            Keyword(49, "명상이 필요한", 0.2f),
        )
    }
}