package com.depromeet.fragraph.feature.meditation.adapter

import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import timber.log.Timber

@BindingAdapter(value = [
    "bind_loading_1",
    "bind_loading_2",
    "bind_loading_3",
], requireAll = false)
fun FrameLayout.bindTwoLoadingState(
    loading1: Boolean = false,
    loading2: Boolean = false,
    loading3: Boolean = false
) {
    this.visibility = if(
        loading1 || loading2 || loading3
    ) View.VISIBLE else View.GONE
}

@BindingAdapter(value = [
    "bind_loading_1",
    "bind_loading_2",
    "bind_loading_3",
], requireAll = false)
fun LottieAnimationView.bindTwoLoadingState(
    loading1: Boolean = false,
    loading2: Boolean = false,
    loading3: Boolean = false
) {
    if(
        loading1 || loading2 || loading3
    ) {
        this.playAnimation()
    } else{
//        this.pauseAnimation()
    }
}