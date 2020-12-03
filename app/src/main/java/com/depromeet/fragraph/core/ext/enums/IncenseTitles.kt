package com.depromeet.fragraph.core.ext.enums

import android.graphics.Color
import com.depromeet.fragraph.R
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

fun IncenseTitle.toNormal(): String {
    return when(this) {
        IncenseTitle.LAVENDER -> "Lavender"
        IncenseTitle.PEPPERMINT -> "Peppermint"
        IncenseTitle.SANDALWOOD -> "Sandalwood"
        IncenseTitle.ORANGE -> "Orange"
        IncenseTitle.EUCALYPTUS -> "Eucalyptus"
        else -> this.name
    }
}

fun IncenseTitle.toEffect(): String {
    return when(this) {
        IncenseTitle.LAVENDER -> "불면증과 우울증 완화"
        IncenseTitle.PEPPERMINT -> "집중력 향상"
        IncenseTitle.SANDALWOOD -> "마음의 안정과 평온"
        IncenseTitle.ORANGE -> "기쁨과 활력"
        IncenseTitle.EUCALYPTUS -> "스트레스 해소"
        else -> "기타 효과"
    }
}

fun IncenseTitle.backgroundResourceId(): Int {
    return when(this) {
        IncenseTitle.LAVENDER -> R.drawable.incense_lavender_background
        IncenseTitle.PEPPERMINT -> R.drawable.incense_peppermint_background
        IncenseTitle.SANDALWOOD -> R.drawable.incense_sandalwood_background
        IncenseTitle.ORANGE -> R.drawable.incense_orange_background
        IncenseTitle.EUCALYPTUS -> R.drawable.incense_eucalyptus_background
        IncenseTitle.EMPTY -> R.drawable.incense_empty_background
        else -> R.drawable.default_history_background
    }
}