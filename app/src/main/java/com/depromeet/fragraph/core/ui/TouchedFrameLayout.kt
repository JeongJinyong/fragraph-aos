package com.depromeet.fragraph.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import timber.log.Timber


class TouchedFrameLayout: FrameLayout {

    private var onTouchChangedListener: OnTouchChangedListener? = null
    var isTouched = false

    constructor(context: Context) : super(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            this.isTouched = true
            onTouchChangedListener?.onTouchChanged()
        }
        if (event.action == MotionEvent.ACTION_UP) {
            this.isTouched = false
            onTouchChangedListener?.onTouchChanged()
        }
        if (event.action == MotionEvent.ACTION_CANCEL) {
            this.isTouched = false
            onTouchChangedListener?.onTouchChanged()
        }
        return true
    }

    fun setOnTouchChangedListener(listener: OnTouchChangedListener) {
        this.onTouchChangedListener = listener
    }

    interface OnTouchChangedListener {
        fun onTouchChanged()
    }
}