package com.depromeet.fragraph.data.local

import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

class LocalData {

    val selectedKeywords = mutableListOf<Keyword>()
    var meditation: Meditation? = null
    var memoCached: Memo? = null

    val categories = listOf(
        Category(1, "명상"),
        Category(2, "집중"),
        Category(3, "숙면"),
        Category(4, "기분전환"),
        Category(5, "스트레스"),
    )

    val incenses = listOf(
        Incense(
            1,
            IncenseTitle.SANDALWOOD,
            "심장기능을 강화하고 혈액순환을 촉진, 마음을 진정시키는 효과가 뛰어납니다.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/sandalwood.png",
            categories[0]
        ),
        Incense(
            2,
            IncenseTitle.PEPPERMINT,
            "기침, 감기, 천식, 알레르기 및 결핵 등 호흡기계에 건강상 효능을 제공, 기억력 증진 및 스트레스 완화 효과가 있을 수 있습니다.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/peppermint.png",
            categories[1]
        ),
        Incense(
            3,
            IncenseTitle.LAVENDER,
            "주성분은 아세트산리날릴, 리날올, 피넨, 리모넨, 게라니올, 시네올 등이다. 이는 신경을 안정시켜주고 스트레스 해소 및 불면증 예방에 탁월한 효과가 있다.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/lavendar.png",
            categories[2]
        ),
        Incense(
            4,
            IncenseTitle.ORANGE,
            "림프 흐름을 자극하여 부은 조직의 치료를 돕고, 셀룰라이트 처치에도 사용됩니다. 건성 피부, 염증이 있는 피부, 여드름 성향의 피부를 진정시키는 데 유용합니다. 또한 재생성이 있어 노화 피부와 거칠고 굳어진 피부치료에 사용하면 좋습니다.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/orange.png",
            categories[3]
        ),
        Incense(
            5,
            IncenseTitle.EUCALYPTUS,
            "근육통 안화효과, 다피가료움증 개선, 호흡기 건강에 도움을 줍니다.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/eucalyptus.png",
            categories[4]
        ),
    )

    val videos = listOf(
        Video(
            1,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_sandalwood.gif"
        ),
        Video(
            2,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_peppermint.gif"
        ),
        Video(
            3,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_lavender.gif"
        ),
        Video(
            4,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_orange.gif"
        ),
        Video(
            5,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_eucalyptus.gif"
        ),
    )

    val musics = listOf(
        Music(
            1,
            "별빛을 보다\n문득",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_sandalwood.mp3"
        ),
        Music(
            2,
            "Lifting\nDreams",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_peppermint.mp3"
        ),
        Music(
            3,
            "Kiss the\nSky",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_lavender.mp3"
        ),
        Music(
            4,
            "Angels\nDream",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_orange.mp3"
        ),
        Music(
            5,
            "Heavenly",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_eucalyptus.mp3"
        ),
    )

    val sandalwoodsKeywords = listOf(
        Keyword(1, "안정이 필요한", 0.3f, categories[0]),
        Keyword(2, "꿀꿀한", 0.3f, categories[0]),
        Keyword(3, "쉬고 싶어", 0.3f, categories[0]),
        Keyword(4, "하루의 마무리", 0.3f, categories[0]),
        Keyword(5, "마음이 뒤숭숭해", 0.3f, categories[0]),
        Keyword(6, "생각이 생각을 불러오는", 0.3f, categories[0]),
        Keyword(7, "너어무 신이나", 0.3f, categories[0]),
        Keyword(8, "생각을 비우고 싶은", 0.3f, categories[0]),
        Keyword(9, "이제는 놓아줘야 해", 0.3f, categories[0]),
        Keyword(10, "아무 의미 없다", 0.3f, categories[0]),
    )

    val peppermintKeywords = listOf(
        Keyword(11, "남다른 집중력이 필요해", 0.3f, categories[1]),
        Keyword(12, "머리가 복잡해", 0.3f, categories[1]),
        Keyword(13, "불안 불안해", 0.3f, categories[1]),
        Keyword(14, "망치면 어떡하지?", 0.3f, categories[1]),
        Keyword(15, "집중이 안되는", 0.3f, categories[1]),
        Keyword(16, "상쾌함이 필요해", 0.3f, categories[1]),
        Keyword(17, "주변이 너무 산만해", 0.3f, categories[1]),
        Keyword(18, "아직 할 일이 남은", 0.3f, categories[1]),
        Keyword(19, "지금은 빡공 할 타이밍", 0.3f, categories[1]),
        Keyword(20, "지금 너무 졸려", 0.3f, categories[1]),
    )

    val lavenderKeywords = listOf(
        Keyword(21, "피곤한데 잠이 안와", 0.3f, categories[2]),
        Keyword(22, "너무너무 우울해", 0.3f, categories[2]),
        Keyword(23, "토닥토닥", 0.3f, categories[2]),
        Keyword(24, "자야 할 시간이야", 0.3f, categories[2]),
        Keyword(25, "좋은 꿈 꾸고 싶어", 0.3f, categories[2]),
        Keyword(26, "혼자이고 싶지만 외로워", 0.3f, categories[2]),
        Keyword(27, "오늘 야근했어", 0.3f, categories[2]),
        Keyword(28, "술 마시고 싶은 하루", 0.3f, categories[2]),
        Keyword(29, "바람 쐬고 싶어", 0.3f, categories[2]),
        Keyword(30, "가슴이 꽉 막혀", 0.3f, categories[2]),
    )

    val orangeKeywords = listOf(
        Keyword(31, "솟아라, 엔돌핀이여", 0.3f, categories[3]),
        Keyword(32, "활기찬 하루", 0.3f, categories[3]),
        Keyword(33, "신나는", 0.3f, categories[3]),
        Keyword(34, "기운 없는", 0.3f, categories[3]),
        Keyword(35, "지친 하루 끝에서", 0.3f, categories[3]),
        Keyword(36, "시체가 되고 싶어", 0.3f, categories[3]),
        Keyword(37, "아무것도 하기 싫은", 0.3f, categories[3]),
        Keyword(38, "번아웃 증후군", 0.3f, categories[3]),
        Keyword(39, "기분전환이 필요해", 0.3f, categories[3]),
        Keyword(40, "하늘을 날아갈 것 같은", 0.3f, categories[3]),
        Keyword(41, "설레는", 0.3f, categories[3]),
        Keyword(42, "웃음이 나오는", 0.3f, categories[3]),
        Keyword(43, "색다른 것을 하고 싶은", 0.3f, categories[3]),
        Keyword(44, "멀리 떠나고 싶은", 0.3f, categories[3]),
    )

    val eucalyptusKeywords = listOf(
        Keyword(45, "마음대로 되지 않는 하루", 0.3f, categories[4]),
        Keyword(46, "스트레스 받아", 0.3f, categories[4]),
        Keyword(47, "머리가 지끈거리는", 0.3f, categories[4]),
        Keyword(48, "혈압 상승 중", 0.3f, categories[4]),
        Keyword(49, "분노 조절 장애", 0.3f, categories[4]),
        Keyword(50, "머리가 띵!", 0.3f, categories[4]),
        Keyword(51, "고단한", 0.3f, categories[4]),
        Keyword(52, "허탈한", 0.3f, categories[4]),
        Keyword(53, "나보고 살쪘대", 0.3f, categories[4]),
        Keyword(54, "살인충동이 느껴져", 0.3f, categories[4]),
        Keyword(55, "나보고 어쩌라고", 0.3f, categories[4]),
        Keyword(56, "쟤는 왜 저래?", 0.3f, categories[4]),
        Keyword(57, "도통 이해가 안 가", 0.3f, categories[4]),
    )
}