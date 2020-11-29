package com.depromeet.fragraph.feature.report.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.core.ext.dpToPx
import timber.log.Timber


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

        val targetCenter = targetView.left + targetView.context.dpToPx(26f).toInt() + targetView.width / 2
        val distance: Int = targetCenter - recyclerView.width / 2
//        Timber.d("margin: ${targetView.context.dpToPx(20f)}")
//        Timber.d("targetView.left: ${targetView.left}") // 가운데 일때의 left 마진
//        Timber.d("targetView.width: ${targetView.width}") // holder 의 width
//        Timber.d("recyclerView.width: ${recyclerView.width}") // recyclerview width
//
//        Timber.d("targetCenter: ${targetCenter}") // targetCenter
//        Timber.d("distance: ${distance}") // distance

        return intArrayOf(distance, 0) // [Horizontal, Vertical] 로 리턴한다.
    }
}