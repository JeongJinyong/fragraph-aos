package com.depromeet.fragraph.feature.recommendation.incense_select.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.domain.model.Keyword
import com.shawnlin.numberpicker.NumberPicker

@BindingAdapter("bind_incense_select_keywords")
fun TextView.bindIncenseSelectKeywords(keywords: List<Keyword>) {
    this.text = keywords.map { "#${it.name}"  }
        .reduce { acc, name -> "$acc $name "}
}

@BindingAdapter("bind_incense_select_playtime")
fun TextView.bindIncenseSelectPlaytime(playtime: Int) {
    val minutes = playtime / 60
    val seconds = playtime - (60 * minutes)
    val minText = if (minutes < 10) "0$minutes" else "$minutes"
    val secText = if (seconds < 10) "0$seconds" else "$seconds"
    this.text = "$minText : $secText"
}