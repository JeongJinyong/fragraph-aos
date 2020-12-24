package com.depromeet.fragraph.data.local

import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

class LocalData {

    var myInfo: User? = null
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
            "샌들우드는 심신의 안정과 평화를\n" +
                    "가져다줘요. 신경의 긴장과 불안을 진정시키는 작용이 우수하여 향을\n" +
                    "맡고 있으면 근심과 걱정이 스르륵 사라지는 것을 느낄 수 있어요.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/image_sandalwood.png",
            categories[0]
        ),
        Incense(
            2,
            IncenseTitle.PEPPERMINT,
            "페퍼민트는 기억력 개선과\n" +
                    "각성 효과가 있어요. 마음을 강하게 해 주고 머리를 맑게 해주어\n" +
                    "새로운 것을 잘 받아들일 수 있도록 도와줘요.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/image_peppermint.png",
            categories[1]
        ),
        Incense(
            3,
            IncenseTitle.LAVENDER,
            "라벤더는 정신적 이완을 도와주어\n" +
                    "불면증, 불안감, 우울증의 증상을\n" +
                    "완화하는 데 효과적이에요. 불안하거나 잠이 오지 않을 때 라벤더 향으로 마음의 안식을 가져보세요.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/image_lavendar.png",
            categories[2]
        ),
        Incense(
            4,
            IncenseTitle.ORANGE,
            "오렌지는 몸과 마음을 밝고\n" +
                    "따뜻하게 하여 활력을 주는 효과가 있어요. 마음이 지치고 울적한 날 \n" +
                    "렌지 향으로 기분을 전환해 보는건\n" +
                    "어떨까요?",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/image_orange.png",
            categories[3]
        ),
        Incense(
            5,
            IncenseTitle.EUCALYPTUS,
            "유칼립투스는 진정작용에 탁월한\n" +
                    "효능이 있다고 알려졌어요.\n" +
                    "예민해진 신경을 이완시켜주고\n" +
                    "스트레스를 해소하는 데 도움을\n" +
                    "줄 거에요.",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/image_eucalyptus.png",
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
            "Siix0 - \nWalking Alone",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_sandalwood.mp3"
        ),
        Music(
            2,
            "Miguel\nJohn -\nPsalms",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_peppermint.mp3"
        ),
        Music(
            3,
            "Chiro -\nSlump",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_lavender.mp3"
        ),
        Music(
            4,
            "Chiro -\nWith my \nold bike",
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_orange.mp3"
        ),
        Music(
            5,
            "Ryan -\nMilk Coffee",
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