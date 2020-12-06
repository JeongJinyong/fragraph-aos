package com.depromeet.fragraph.feature.report.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.depromeet.fragraph.base.GlideApp
import com.depromeet.fragraph.core.ext.enums.backgroundResourceId
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.feature.report.view.chart.ReportChartView


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