package com.depromeet.fragraph.feature.report.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.GlideApp
import com.depromeet.fragraph.core.ext.FRAGRAPH_HISTORY_FORMAT
import com.depromeet.fragraph.core.ext.JUST_DAY
import com.depromeet.fragraph.core.ext.enums.backgroundResourceId
import com.depromeet.fragraph.core.ext.miliSecondsToStringFormat
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.feature.report.view.ReportChartView


@BindingAdapter("bind_incense", "bind_bar_data", requireAll = true)
fun ReportChartView.setBarData(incenses: List<IncenseTitle>, playCounts: List<Float>) {
    setDataAndStyle(incenses, playCounts)
    animateY(1000)
    invalidate()
}

@BindingAdapter("bind_history_background")
fun ImageView.bindHistoryBackground(value: IncenseTitle) {
    GlideApp.with(this.context)
        .load(value.backgroundResourceId())
        .placeholder(ColorDrawable(Color.GRAY))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}