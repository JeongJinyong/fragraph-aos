package com.depromeet.fragraph.feature.home.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.core.ext.dpToPx


class HistoryRecyclerViewSnapHelper(
    private val recyclerView: RecyclerView
): PagerSnapHelper() {

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val pos: Int = recyclerView.getChildAdapterPosition(targetView)

        // 첫번째면 그대로 가면 된다.
        if (pos == 0) {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView)
        }

        val targetCenter = targetView.left + targetView.width / 2 // item 의 중앙값윛
        val wantedCenter = (targetView.context.dpToPx(20f).toInt() + targetView.width / 2) // 내가 원하는 중앙값
        val distance: Int = targetCenter - wantedCenter // distance = 0 이 될때까지 움직임

        return intArrayOf(distance, 0) // [Horizontal, Vertical] 로 리턴한다.
    }
}