package com.depromeet.fragraph.feature.recommendation.keyword_select.model

import androidx.lifecycle.MutableLiveData

data class KeywordUiModel(
    val id : Int,
    val name: String,
    val weight: Float,
    val selected: MutableLiveData<Boolean>,
) {
    fun keywordClick() {
        this.selected.value?.let {
            this.selected.value = !it
        }
    }
}