package com.depromeet.fragraph.feature.meditation.adapter

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.ext.enums.toMeditationTitle
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import timber.log.Timber


@BindingAdapter("bind_incense_title_meditation")
fun TextView.bindIncenseTitleMeditation(value: IncenseTitle) {
    this.text = value.toMeditationTitle()
}

@BindingAdapter(
    "bind_dual_memo_dialog_visibility",
    "bind_dual_select_dialog_visibility",
    requireAll = true
)
fun ConstraintLayout.bindDialogsVisibility(memoVisibility: Boolean, selectVisibility: Boolean) {
    if (memoVisibility || selectVisibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("bind_is_motion_playing")
fun ImageView.bindPlayingMotionAnim(isMotionPlaying: Boolean) {
    if (isMotionPlaying) {
        val rotation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        this.startAnimation(rotation)
    } else {
        this.clearAnimation()
    }
}

@BindingAdapter("bind_bg_playing")
fun ImageView.bindBgPlaying(isPlaying: Boolean) {
    if (isPlaying) {
        val drawable: Drawable? = this.drawable
        if (drawable !== null && drawable is Animatable) {
            drawable.start()
        }
    } else {
        val drawable: Drawable? = this.drawable
        if (drawable !== null && drawable is Animatable) {
            drawable.stop()
        }
    }
}

@BindingAdapter(
    value = [
        "bind_memo_dialog_visible",
        "bind_select_dialog_visible",
    ], requireAll = false
)
fun Button.bindMemoBtnVisible(
    state1: Boolean = false,
    state2: Boolean = false,
) {
    this.visibility = if(state1 || state2) View.GONE else View.VISIBLE
}