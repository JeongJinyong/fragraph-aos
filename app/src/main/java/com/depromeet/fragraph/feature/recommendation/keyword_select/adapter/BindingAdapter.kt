package com.depromeet.fragraph.feature.recommendation.keyword_select.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.R
import com.depromeet.fragraph.feature.recommendation.keyword_select.model.KeywordNextStatus

@BindingAdapter("bind_next_status_text_color")
fun TextView.bindNextStatusTextColor(value: KeywordNextStatus) {
    when (value) {
        KeywordNextStatus.NORMAL -> {
            this.setTextColor(this.context.getColor(R.color.colorBlackGray_3))
        }
        KeywordNextStatus.LESS -> {
            this.setTextColor(this.context.getColor(R.color.colorRed))
        }
        KeywordNextStatus.OK -> {
            this.setTextColor(this.context.getColor(R.color.colorOrange))
        }
    }
}