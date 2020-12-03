package com.depromeet.fragraph.feature.report.adapter.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.core.ext.dpToPx

class HistoryRecyclerViewDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount;

        if (position == itemCount -1) { // 마지막 아이템인 경우
            outRect.left = view.context.dpToPx(20f).toInt()
            outRect.right = parent.width - view.width -  view.context.dpToPx(20f).toInt()

        } else {
            outRect.left = view.context.dpToPx(20f).toInt()
        }

//        if (position == 0) { // 첫번째 아이템인 경우
//            outRect.left = view.context.dpToPx(20f).toInt()
//            outRect.right = view.context.dpToPx(10f).toInt()
//        } else if(position == itemCount -1) { // 마지막 아이템인 경우
//            outRect.left = view.context.dpToPx(10f).toInt()
//            outRect.right = view.context.dpToPx(20f).toInt()
//        } else { // 그 외 경우
//            outRect.left = view.context.dpToPx(10f).toInt()
//            outRect.right = view.context.dpToPx(10f).toInt()
//        }
    }
}