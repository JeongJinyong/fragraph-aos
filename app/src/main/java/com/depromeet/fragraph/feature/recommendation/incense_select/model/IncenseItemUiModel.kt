package com.depromeet.fragraph.feature.recommendation.incense_select.model

import androidx.lifecycle.MutableLiveData
import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

data class IncenseItemUiModel(
    val id: Int,
    val title: IncenseTitle,
    val content: String,
    val image: String,
    val category: Category,
    val video: Video,
    val music: Music,
    val keywords: List<Keyword>,
    val isTouched: MutableLiveData<Boolean>,
    val isCenter: MutableLiveData<Boolean>
) {
    fun changeTouchState() {
        this.isTouched.value?.let {
            this.isTouched.value = !it
        }
    }

    fun changeCenterPosition(isCenter: Boolean) {
        this.isCenter.value = isCenter
    }

    fun toIncense() = Incense(this.id, this.title, this.content, this.image)
}