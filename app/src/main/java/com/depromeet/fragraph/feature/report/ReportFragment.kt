package com.depromeet.fragraph.feature.report

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.FragmentReportBinding
import com.depromeet.fragraph.databinding.FragmentSplashBinding
import com.depromeet.fragraph.feature.report.view.HistoryRecyclerViewAdapter
import com.depromeet.fragraph.feature.report.viewmodel.ReportViewModel
import com.depromeet.fragraph.feature.signin.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment: Fragment(R.layout.fragment_report) {

    private val reportViewModel: ReportViewModel by viewModels()

    var mIsBackVisible = false

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
    }
}