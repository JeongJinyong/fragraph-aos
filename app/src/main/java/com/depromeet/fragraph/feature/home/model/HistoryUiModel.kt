package com.depromeet.fragraph.feature.home.model

import androidx.lifecycle.MutableLiveData
import com.depromeet.fragraph.domain.model.History
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.model.Memo
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class HistoryUiModel(
    val id: Int,
    val createdAt: Long,
    val playTime: String,
    val incenseTitle: IncenseTitle,
    val memo: Memo?,
    val selectedKeywords: List<Keyword>,
    val unselectedKeywords: List<Keyword>,
//    val keywordFirst: String,
//    val keywordSecond: String?,
//    val keywordThird: String?,
    val isExisted: Boolean,
    var isBack: Boolean,
    val isCenter: MutableLiveData<Boolean>,
    val moreClickCallback: (history: HistoryUiModel) -> Unit,
) {
    fun changeCenterPosition(isCenter: Boolean) {
        this.isCenter.value = isCenter
    }

    fun moreClick() {
        moreClickCallback(this)
    }
}

fun getEmptyUiModel(createdAt: Long, isCenter: Boolean) = HistoryUiModel(
    0, createdAt, "0", IncenseTitle.EMPTY,
    Memo(0, "", "", null), listOf(), listOf(),
    isExisted = false, isBack = false,
    isCenter = MutableLiveData(isCenter),
) { history -> Unit}