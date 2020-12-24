package com.depromeet.fragraph.core.ext.enums

import android.graphics.Color
import com.depromeet.fragraph.R
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

fun IncenseTitle.toNormal(): String {
    return when(this) {
        IncenseTitle.SANDALWOOD -> "Sandalwood" // Siix0 - Walking Alone
        IncenseTitle.PEPPERMINT -> "Peppermint" // Miguel John - Psalms [No copyright]
        IncenseTitle.LAVENDER -> "Lavender" // Chiro - Slump
        IncenseTitle.ORANGE -> "Orange" // Chiro - With My old bike
        IncenseTitle.EUCALYPTUS -> "Eucalyptus" // Ryan - Milk Coffee
        else -> this.name
    }
}

fun IncenseTitle.toEffect(): String {
    return when(this) {
        IncenseTitle.SANDALWOOD -> "마음의 안정과 평온"
        IncenseTitle.PEPPERMINT -> "기억력 개선과 집중력 향상"
        IncenseTitle.LAVENDER -> "불면증과 우울증 완화"
        IncenseTitle.ORANGE -> "기쁨과 활력, 기분전환"
        IncenseTitle.EUCALYPTUS -> "스트레스 해소"
        else -> "기타 효과"
    }
}

fun IncenseTitle.toMeditationTitle(): String {
    return when(this) {
        IncenseTitle.SANDALWOOD -> "Sandal\nwood"
        IncenseTitle.PEPPERMINT -> "Pepper\nmint"
        IncenseTitle.LAVENDER -> "Lavender"
        IncenseTitle.ORANGE -> "Orange"
        IncenseTitle.EUCALYPTUS -> "Eucalyptus"
        else -> "기타 효과"
    }
}

fun IncenseTitle.backgroundResourceId(): Int {
    return when(this) {
        IncenseTitle.SANDALWOOD -> R.drawable.incense_sandalwood_background
        IncenseTitle.PEPPERMINT -> R.drawable.incense_peppermint_background
        IncenseTitle.LAVENDER -> R.drawable.incense_lavender_background
        IncenseTitle.ORANGE -> R.drawable.incense_orange_background
        IncenseTitle.EUCALYPTUS -> R.drawable.incense_eucalyptus_background
        IncenseTitle.EMPTY -> R.drawable.incense_empty_background
        else -> R.drawable.default_history_background
    }
}