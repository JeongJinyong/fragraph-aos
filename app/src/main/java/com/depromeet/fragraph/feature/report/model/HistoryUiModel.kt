package com.depromeet.fragraph.feature.report.model

import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Memo

data class HistoryUiModel(
    val id: Int,
    val day: Long,
    val playTime: Int,
    val incense: Incense,
    val memo: Memo,
    var isBack: Boolean,
)