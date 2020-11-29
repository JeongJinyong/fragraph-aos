package com.depromeet.fragraph.feature.report.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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

@BindingAdapter("bind_history_date")
fun TextView.bindHistoryDate(value: Long) {
    this.text = "${value.miliSecondsToStringFormat(FRAGRAPH_HISTORY_FORMAT)}. ${value.miliSecondsToStringFormat(JUST_DAY)}요일"
}

@BindingAdapter("bind_history_background")
fun ImageView.bindHistoryBackground(value: IncenseTitle) {
    GlideApp.with(this.context)
        .load(value.backgroundResourceId())
        .placeholder(ColorDrawable(Color.GRAY))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("bind_history_alpha_anim")
fun FrameLayout.bindHistoryAlphaAnim(visibility: Int) {
    if (visibility == View.VISIBLE && this.visibility == View.INVISIBLE) {
        // 알파값 생성
//        val anim = AnimationUtils.loadAnimation(this.context, R.anim.alpha_history_other)
//        this.startAnimation(anim)
//        anim.setAnimationListener(object: Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {}
//
//            override fun onAnimationEnd(animation: Animation?) {
//                this@bindHistoryAlphaAnim.alpha = 0.38f
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {}
//
//        })
        this.visibility = View.VISIBLE
    }

    if (visibility == View.INVISIBLE && this.visibility == View.VISIBLE) {
        // 알파값 삭제
//        val anim = AnimationUtils.loadAnimation(this.context, R.anim.alpha_history_center)
//        this.startAnimation(anim)
        this.visibility = View.INVISIBLE
    }
}