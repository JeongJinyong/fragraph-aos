package com.depromeet.fragraph.feature.report.adapter

import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.feature.report.view.ReportChartView

@BindingAdapter("bind_incense", "bind_bar_data", requireAll = true)
fun ReportChartView.setBarData(incenses: List<IncenseTitle>, playCounts: List<Float>) {
    setDataAndStyle(incenses, playCounts)
    animateY(1000)
    invalidate()
}