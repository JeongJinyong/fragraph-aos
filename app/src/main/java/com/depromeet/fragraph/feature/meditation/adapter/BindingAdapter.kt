package com.depromeet.fragraph.feature.meditation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.core.ext.enums.toMeditationTitle
import com.depromeet.fragraph.domain.model.enums.IncenseTitle

@BindingAdapter("bind_incense_title_meditation")
fun TextView.bindIncenseTitleMeditation(value: IncenseTitle) {
    this.text = value.toMeditationTitle()
}