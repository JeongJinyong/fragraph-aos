package com.depromeet.fragraph.feature.recommendation.incense_select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentIncenseSelectBinding
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewAdapter
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewDecoration
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewScrollListener
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewSnapHelper
import com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel.IncenseSelectViewModel
import com.depromeet.fragraph.feature.report.adapter.recyclerview.HistoryRecyclerViewScrollListener


class IncenseSelectFragment: Fragment(R.layout.fragment_incense_select) {

    private val incenseSelectViewModel: IncenseSelectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentIncenseSelectBinding.bind(view)
            .apply {
                vm = incenseSelectViewModel
                lifecycleOwner = this@IncenseSelectFragment
            }

        binding.rvIncensesRecommendation.apply {
            val snapHelper = IncenseRecyclerViewSnapHelper(this)
            val incenseAdapter = IncenseRecyclerViewAdapter(viewLifecycleOwner) {
                binding.indicatorIncensesRecommendation.attachToRecyclerView(this, snapHelper)
            }
            val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            adapter = incenseAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(IncenseRecyclerViewDecoration())
            addOnScrollListener(IncenseRecyclerViewScrollListener(linearLayoutManager, incenseAdapter, 2))
            snapHelper.attachToRecyclerView(this)
        }

        incenseSelectViewModel.backClickEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })

        incenseSelectViewModel.openMeditationEvent.observe(viewLifecycleOwner, EventObserver {
            goMeditation()
        })
    }

    private fun goMeditation() {
        findNavController().navigate(R.id.action_incenseSelectFragment_to_meditationFragment)
    }
}