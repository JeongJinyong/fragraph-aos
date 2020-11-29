package com.depromeet.fragraph.feature.report.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.feature.report.model.HistoryUiModel
import timber.log.Timber

class HistoryRecyclerViewScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val historyAdapter: HistoryRecyclerViewAdapter,
): RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val firstPosition = layoutManager.findFirstVisibleItemPosition()
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        if (firstPosition == 0 && lastPosition == 1) { // firstPosition == 0 &  lastPosition == 1 (첫번째인 경우)
            // 첫번째를 보여준다. (뒤를 가린다)
            historyAdapter.changeCenterValue(firstPosition, isCenter = true)
            historyAdapter.changeCenterValue(lastPosition, isCenter = false)

        } else if (firstPosition == itemCount -2 && lastPosition == itemCount -1) { // firstPosition == 갯수 -2 && lastPosition == 갯수 -1 (마지막 경우)
            // 마지막을 보여준다. (앞을 가린다)
            historyAdapter.changeCenterValue(firstPosition, isCenter = false)
            historyAdapter.changeCenterValue(lastPosition, isCenter = true)
        } else if (firstPosition == lastPosition) {
            // 아무것도 하지 않는다.
        } else { // lastPosition - firstPosition = 2 (중간 경우)
            // 가운데를 보여준다 (양쪽을 가린다.)
            historyAdapter.changeCenterValue(firstPosition, isCenter = false)
            historyAdapter.changeCenterValue(firstPosition + 1, isCenter = true)
            historyAdapter.changeCenterValue(lastPosition, isCenter = false)
        }
    }
}