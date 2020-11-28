package com.depromeet.fragraph.feature.report.model

import com.depromeet.fragraph.domain.model.Memo
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class HistoryUiModel(
    val id: Int,
    val createdAt: String,
    val playTime: String,
    val incenseTitle: IncenseTitle,
    val memo: Memo,
    val isExisted: Boolean,
    var isBack: Boolean,
)

fun getEmptyUiModel(createdAt: String) = HistoryUiModel(
    0, createdAt, "0", IncenseTitle.LAVENDER, Memo(0, "", null), isExisted = true, isBack = false
)