package com.depromeet.fragraph.core.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

class RemovePaddingView : LinearLayout {

    constructor(context: Context) : super(context, null) {
        initView(null, 0)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs, defStyleAttr)
    }

    private lateinit var textView: TextView
    fun getTextView(): TextView? {
        return textView
    }

    init {

    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        textView = if (attrs != null && defStyleAttr > 0) {
            RemovePaddingTextViewInner(context, attrs, defStyleAttr)
        } else if (attrs != null) {
            RemovePaddingTextViewInner(context, attrs)
        } else {
            RemovePaddingTextViewInner(context)
        }
        setPadding(0, 0, 0, 0)
        addView(textView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        width += textView.paddingLeft + textView.paddingRight
        setMeasuredDimension(width, measuredHeight)
    }

    class RemovePaddingTextViewInner  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ): AppCompatTextView(context, attrs, defStyleAttr) {

        // Set whether to remove the spacing, true is remove
        private var noDefaultPadding: Boolean = true
        private var fontMetricsInt: Paint.FontMetricsInt = Paint.FontMetricsInt()
        private var minRect: Rect = Rect()

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            paint.getFontMetricsInt(fontMetricsInt)
            paint.getTextBounds(text.toString(), 0, text.length, minRect)
            val lp = this.layoutParams as MarginLayoutParams
            lp.topMargin = -(fontMetricsInt.bottom - minRect.bottom) + (fontMetricsInt.top - minRect.top)
            lp.rightMargin = -(minRect.left + (measuredWidth - minRect.right))
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

        override fun onDraw(canvas: Canvas?) {

            if (noDefaultPadding) {
                paint.getFontMetricsInt(fontMetricsInt)
                paint.getTextBounds(text.toString(), 0, text.length, minRect)
                canvas?.translate(
                    -minRect.left.toFloat(),
                    (fontMetricsInt.bottom - minRect.bottom).toFloat()
                )
            }
            super.onDraw(canvas)
        }

        override fun setText(text: CharSequence?, type: BufferType?) {
            super.setText(text, type)
            this.requestLayout()
        }
    }
}