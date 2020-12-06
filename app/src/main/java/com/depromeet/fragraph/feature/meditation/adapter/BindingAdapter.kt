package com.depromeet.fragraph.feature.meditation.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.core.ext.enums.toMeditationTitle
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import jp.wasabeef.blurry.Blurry

@BindingAdapter("bind_incense_title_meditation")
fun TextView.bindIncenseTitleMeditation(value: IncenseTitle) {
    this.text = value.toMeditationTitle()
}

@BindingAdapter("bind_meditation_memo_background")
fun ImageView.bindIncenseMeditationMemoBackground(visibility: Int) {
    this.visibility = visibility
    if (visibility == View.VISIBLE) {
        Blurry.with(context)
            .radius(10)
            .sampling(8)
            .color(Color.argb(66, 255, 255, 0))
            .async()
            .animate(500)
            .onto(this.parent as ViewGroup)
    }
}

@BindingAdapter("bind_dual_memo_dialog_visibility", "bind_dual_select_dialog_visibility", requireAll = true)
fun ConstraintLayout.bindDialogsVisibility(memoVisibility: Boolean, selectVisibility: Boolean) {
    if (memoVisibility || selectVisibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}