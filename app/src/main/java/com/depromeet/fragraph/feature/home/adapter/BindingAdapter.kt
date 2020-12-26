package com.depromeet.fragraph.feature.home.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.GlideApp
import com.depromeet.fragraph.core.ext.enums.backgroundResourceId
import com.depromeet.fragraph.core.ui.TextOutLineView
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.feature.home.view.chart.ReportChartView
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.bindIncenseSelectKeywords
import timber.log.Timber


@BindingAdapter("bind_incense", "bind_bar_data", requireAll = true)
fun ReportChartView.setBarData(incenses: List<IncenseTitle>, playCounts: List<Float>) {
    setDataAndStyle(incenses, playCounts)
    animateY(400)
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

@BindingAdapter("bind_selected_keyword", "bind_unselected_keyword", "bind_keywords_index", requireAll = true)
fun TextOutLineView.bindKeywords(selectedKeywords: List<Keyword>, unselectedKeywords: List<Keyword>, index: Int) {
    if (selectedKeywords.size > index) {
        this.text = context.getString(R.string.keyword_format, selectedKeywords[index].name)
        this.setTextColor(context.getColor(R.color.colorWhiteGray_6))
        return
    }

    if (selectedKeywords.size + unselectedKeywords.size > index) {
        val unselectedIndex = index - selectedKeywords.size
        this.text = context.getString(R.string.keyword_format, unselectedKeywords[unselectedIndex].name)
        return
    }
}