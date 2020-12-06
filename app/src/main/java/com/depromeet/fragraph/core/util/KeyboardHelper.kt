package com.depromeet.fragraph.core.util

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
import android.widget.PopupWindow

class KeyboardHelper(
    private val rootView: View,
    private val keyboardHeightCallback: (height: Int) -> Unit
): PopupWindow() {

    private var originHeight = -1

    init {
        initialize()
    }

    private fun initialize() {
        softInputMode = SOFT_INPUT_STATE_ALWAYS_VISIBLE
        inputMethodMode = INPUT_METHOD_NEEDED

        width = 0
        height = MATCH_PARENT

        rootView.viewTreeObserver?.addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener {
            keyboardHeightCallback(getKeyboardHeight())
        }))
    }

    private fun getKeyboardHeight(): Int {
        // originalHeight = 전체 화면 height
        if (rootView.height > originHeight) {
            originHeight = rootView.height
        }
        // 보이는 화면의 높이를 구한다.
        val visibleFrameSize = Rect()
        rootView.getWindowVisibleDisplayFrame(visibleFrameSize)
        val visibleFrameHeight: Int = visibleFrameSize.bottom - visibleFrameSize.top

        // 전체 - 보여지는 화면의 높이 = keyboard 높이
        return originHeight - visibleFrameHeight
    }
}