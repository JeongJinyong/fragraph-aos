package com.depromeet.fragraph.feature.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentReportBinding
import com.depromeet.fragraph.feature.report.adapter.recyclerview.*
import com.depromeet.fragraph.feature.report.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

// Todo 이미지 있다면 클릭시 확대 애니메이션 적용 필요
// Todo 알파값 애니메이션 변경 필요

@AndroidEntryPoint
class ReportFragment: Fragment(R.layout.fragment_report) {

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentReportBinding.bind(view)
            .apply {
                vm = reportViewModel
                lifecycleOwner = this@ReportFragment
            }


        // animation test
        val distance = 80000
        val scale: Float = resources.displayMetrics.density * distance
        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val historyAdapter = HistoryRecyclerViewAdapter(viewLifecycleOwner, scale) {position ->
            binding.rvHistories.scrollToPosition(position)
        }
        binding.rvHistories.apply {
//            adapter = historyAdapter
            adapter = historyAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(HistoryRecyclerViewDecoration())
            addOnScrollListener(HistoryRecyclerViewScrollListener(linearLayoutManager, historyAdapter, 2))

            val snapHelper = HistoryRecyclerViewSnapHelper(this)
            snapHelper.attachToRecyclerView(this)
        }

        reportViewModel.openRecommendationEvent.observe(viewLifecycleOwner, EventObserver {
            goKeywordSelect()
        })
    }

    private fun goKeywordSelect() {
        findNavController().navigate(R.id.action_reportFragment_to_keywordSelectFragment)
    }
}