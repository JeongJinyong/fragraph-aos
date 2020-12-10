package com.depromeet.fragraph.feature.recommendation.incense_select.adapter

import android.graphics.Rect
import android.view.MotionEvent
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.ui.TouchedFrameLayout
import com.depromeet.fragraph.domain.model.Keyword
import timber.log.Timber


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


@BindingAdapter("set_incense_touched")
fun TouchedFrameLayout.setPressChanged(isTouched: Boolean) {
    this.isTouched = isTouched
}

@InverseBindingAdapter(attribute = "set_incense_touched", event = "bind_incense_touched_changed")
fun TouchedFrameLayout.getTouched(): Boolean {
    return this.isTouched
}

@BindingAdapter("bind_incense_touched_changed", requireAll = true)
fun TouchedFrameLayout.bindIncenseTouchedChangedListener(listener: InverseBindingListener?) {
    this.setOnTouchChangedListener(object : TouchedFrameLayout.OnTouchChangedListener {
        override fun onTouchChanged() {
            listener?.onChange()
        }
    })
}