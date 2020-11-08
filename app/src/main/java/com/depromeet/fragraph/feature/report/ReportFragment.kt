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
import com.depromeet.fragraph.feature.report.adapter.HistoryRecyclerViewAdapter
import com.depromeet.fragraph.feature.report.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        binding.rvHistories.apply {
            adapter = HistoryRecyclerViewAdapter(scale)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        reportViewModel.openRecommendationEvent.observe(viewLifecycleOwner, EventObserver {
            goFeelingSelect()
        })
    }

    private fun goFeelingSelect() {
        findNavController().navigate(R.id.action_reportFragment_to_feelingSelectFragment)
    }
}