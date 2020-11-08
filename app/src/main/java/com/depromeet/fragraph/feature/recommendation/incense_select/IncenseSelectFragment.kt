package com.depromeet.fragraph.feature.recommendation.incense_select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentIncenseSelectBinding
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewAdapter
import com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel.IncenseSelectViewModel


class IncenseSelectFragment: Fragment(R.layout.fragment_incense_select) {

    private val incenseSelectViewModel: IncenseSelectViewModel by viewModels()

    private val incenseRecyclerViewAdapter: IncenseRecyclerViewAdapter by lazy {
        IncenseRecyclerViewAdapter(this@IncenseSelectFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentIncenseSelectBinding.bind(view)
            .apply {
                vm = incenseSelectViewModel
                lifecycleOwner = this@IncenseSelectFragment
            }

        binding.rvIncenses.apply {
            adapter = incenseRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvIncenses)

        incenseSelectViewModel.openMeditationEvent.observe(viewLifecycleOwner, EventObserver {
            goMeditation()
        })
    }

    private fun goMeditation() {
        findNavController().navigate(R.id.action_incenseSelectFragment_to_meditationFragment)
    }
}