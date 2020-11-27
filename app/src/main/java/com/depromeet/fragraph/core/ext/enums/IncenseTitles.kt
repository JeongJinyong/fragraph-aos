package com.depromeet.fragraph.core.ext.enums

import android.graphics.Color
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