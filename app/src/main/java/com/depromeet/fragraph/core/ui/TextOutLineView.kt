package com.depromeet.fragraph.core.ui

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.ext.dpToPx


class TextOutLineView: AppCompatTextView {

    var stroke = false
    var strokeWidth = 0.0f
    var strokeColor: Int = 0xffffff

    constructor(context: Context) : super(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TextOutLineView)
        stroke = a.getBoolean(R.styleable.TextOutLineView_text_stroke, false);
        strokeWidth = a.getFloat(R.styleable.TextOutLineView_text_stroke_width_dp, 0.0f)
        strokeColor = a.getColor(R.styleable.TextOutLineView_text_stroke_color, 0xffffff)
    }

    override fun onDraw(canvas: Canvas?) {
        if (stroke) {
            val states: ColorStateList = textColors;
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = context.dpToPx(strokeWidth)
            setTextColor(strokeColor)
            super.onDraw(canvas)
            paint.style = Paint.Style.FILL
            setTextColor(states)
        }

        super.onDraw(canvas)
    }
}