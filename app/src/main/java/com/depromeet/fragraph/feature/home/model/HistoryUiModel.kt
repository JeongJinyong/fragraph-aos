package com.depromeet.fragraph.feature.home.model

import androidx.lifecycle.MutableLiveData
import com.depromeet.fragraph.domain.model.Memo
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class HistoryUiModel(
    val id: Int,
    val createdAt: Long,
    val playTime: String,
    val incenseTitle: IncenseTitle,
    val memo: Memo?,
    val keywordFirst: String,
    val keywordSecond: String?,
    val keywordThird: String?,
    val isExisted: Boolean,
    var isBack: Boolean,
    val isCenter: MutableLiveData<Boolean>,
) {
    fun changeCenterPosition(isCenter: Boolean) {
        this.isCenter.value = isCenter
    }
}

fun getEmptyUiModel(createdAt: Long, isCenter: Boolean) = HistoryUiModel(
    0, createdAt, "0", IncenseTitle.EMPTY,
    Memo(0, "", "", null), "", "", "",
    isExisted = false, isBack = false,
    isCenter = MutableLiveData(isCenter),
)