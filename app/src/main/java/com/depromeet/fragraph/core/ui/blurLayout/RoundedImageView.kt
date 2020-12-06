package com.depromeet.fragraph.core.ui.blurLayout

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class RoundedImageView : AppCompatImageView {
    var cornerRadius = 0f
    private var rectF: RectF
    private var porterDuffXfermode: PorterDuffXfermode

    constructor(context: Context) : super(context, null) {
        rectF = RectF()
        porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    constructor(context: Context, attributes: AttributeSet?) : super(context, attributes) {
        rectF = RectF()
        porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    override fun onDraw(canvas: Canvas) {
        val myDrawable: Drawable? = drawable
        if (myDrawable != null && myDrawable is BitmapDrawable && cornerRadius > 0) {
            rectF.set(myDrawable.getBounds())
            val prevCount: Int = canvas.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG)
            imageMatrix.mapRect(rectF)
            val paint: Paint = myDrawable.paint
            paint.isAntiAlias = true
            paint.color = DEFAULT_COLOR
            val prevMode: Xfermode = paint.xfermode
            canvas.drawARGB(DEFAULT_RGB, DEFAULT_RGB, DEFAULT_RGB, DEFAULT_RGB)
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
            paint.xfermode = porterDuffXfermode
            super.onDraw(canvas)
            paint.xfermode = prevMode
            canvas.restoreToCount(prevCount)
        } else {
            super.onDraw(canvas)
        }
    }

    companion object {
        const val DEFAULT_COLOR = -0x1000000
        const val DEFAULT_RGB = 0
    }
}