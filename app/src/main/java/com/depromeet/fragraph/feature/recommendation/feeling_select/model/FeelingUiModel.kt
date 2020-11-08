package com.depromeet.fragraph.feature.recommendation.feeling_select.model

import androidx.lifecycle.MutableLiveData

data class FeelingUiModel(
    val id : Int,
    val name: String,
    val weight: Float,
    val selected: MutableLiveData<Boolean>,
)

fun FeelingUiModel.feelingClick() {
    this.selected.value?.let {
        this.selected.value = !it
    }
}