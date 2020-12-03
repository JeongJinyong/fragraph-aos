package com.depromeet.fragraph.feature.recommendation.incense_select.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.core.ext.dpToPx
import timber.log.Timber


class IncenseRecyclerViewSnapHelper(
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

        val targetCenter = targetView.left + targetView.width / 2
        val distance: Int = targetCenter - (targetView.context.dpToPx(20f).toInt() + targetView.width / 2)
        return intArrayOf(distance, 0) // [Horizontal, Vertical] 로 리턴한다.
    }
}