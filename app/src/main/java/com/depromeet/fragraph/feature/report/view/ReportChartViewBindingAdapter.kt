package com.depromeet.fragraph.feature.report.view

import androidx.databinding.BindingAdapter

@BindingAdapter("bind_day", "bind_bar_data", requireAll = true)
fun ReportChartView.setBarData(days: List<String>, playTimes: List<Float>) {
    setDataAndStyle(days, playTimes)
    animateY(1000)
    invalidate()
}