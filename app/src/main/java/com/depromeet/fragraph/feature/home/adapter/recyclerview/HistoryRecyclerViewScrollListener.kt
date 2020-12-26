package com.depromeet.fragraph.feature.home.adapter.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryRecyclerViewScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val historyAdapter: HistoryRecyclerViewAdapter,
    private val visibleCount: Int
): RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val firstPosition = layoutManager.findFirstVisibleItemPosition()
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        // firstPosition == 0 &  lastPosition == 1 (첫번째인 경우)
        if (firstPosition == 0 && lastPosition == 1) {
            // 첫번째를 보여준다. (뒤를 가린다)
            historyAdapter.changeCenterValue(firstPosition, isCenter = true)
            historyAdapter.changeCenterValue(lastPosition, isCenter = false)
            return
        }

        if (visibleCount == 2) {

            // 첫번째 것이 보이는 경우
            if (lastPosition - firstPosition == 1) {
                // 새로 생기는 부분 보여줌
                historyAdapter.changeCenterValue(firstPosition - 1, isCenter = false)
                historyAdapter.changeCenterValue(firstPosition, isCenter = true)
                historyAdapter.changeCenterValue(lastPosition, isCenter = false)
                return
            }

            // 마지막인 경우
            if (firstPosition == lastPosition) {
                historyAdapter.changeCenterValue(firstPosition, isCenter = false)
                historyAdapter.changeCenterValue(lastPosition, isCenter = true)
                return
            }
        }

        if (visibleCount == 3) {
            // 양쪽이 보이는 경우
            if (lastPosition - firstPosition == 2) {
                // 가운데를 보여준다 (양쪽을 가린다.)
                historyAdapter.changeCenterValue(firstPosition, isCenter = false)
                historyAdapter.changeCenterValue(firstPosition + 1, isCenter = true)
                historyAdapter.changeCenterValue(lastPosition, isCenter = false)
                return
            }

            // (마지막 경우)
            if (firstPosition == itemCount -2 && lastPosition == itemCount -1) {
                // 마지막을 보여준다. (앞을 가린다)
                historyAdapter.changeCenterValue(firstPosition, isCenter = false)
                historyAdapter.changeCenterValue(lastPosition, isCenter = true)
                return
            }

            // 마지막에서 슬라이드 하는 경우
            if (firstPosition == lastPosition) {
                // 아무것도 하지 않는다.
                return
            }
        }
    }
}