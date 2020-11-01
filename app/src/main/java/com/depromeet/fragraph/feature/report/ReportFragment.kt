package com.depromeet.fragraph.feature.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.FragmentReportBinding
import com.depromeet.fragraph.databinding.FragmentSplashBinding
import com.depromeet.fragraph.feature.report.viewmodel.ReportViewModel
import com.depromeet.fragraph.feature.signin.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment: Fragment(R.layout.fragment_report) {

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentReportBinding.bind(view)
            .apply {
                vm = reportViewModel;
                lifecycleOwner = this@ReportFragment
            }
    }
}