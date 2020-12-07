package com.depromeet.fragraph.feature.recommendation.keyword_select.model

import androidx.lifecycle.MutableLiveData
import com.depromeet.fragraph.domain.model.Category

data class KeywordUiModel(
    val id : Int,
    val name: String,
    val weight: Float,
    val category: Category,
    val selected: MutableLiveData<Boolean>,
) {
    fun keywordClick() {
        this.selected.value?.let {
            this.selected.value = !it
        }
    }
}