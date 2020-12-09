package com.depromeet.fragraph.feature.recommendation.incense_select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.SharedViewModel
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentIncenseSelectBinding
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewAdapter
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewDecoration
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewScrollListener
import com.depromeet.fragraph.feature.recommendation.incense_select.adapter.IncenseRecyclerViewSnapHelper
import com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel.IncenseSelectViewModel
import com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel.PlaytimePickerViewModel
import dagger.hilt.android.AndroidEntryPoint

// Todo  alpha 처리를 에 전체 말고 폰트만 애니메이션 나오게 수정
// Todo 디테일은 이미지 화면 탭시 보이고 손때면 안보이게
// Todo #999999 로 time 이 엑티브 되지 않았으면 꺼지고 disabled
// Todo timepicker 폰트 먹이기(gilory)

@AndroidEntryPoint
class IncenseSelectFragment: Fragment(R.layout.fragment_incense_select) {

    private val incenseSelectViewModel: IncenseSelectViewModel by viewModels()

    private val playtimePickerViewModel: PlaytimePickerViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainBinding = FragmentIncenseSelectBinding.bind(view)
            .apply {
                vm = incenseSelectViewModel
                lifecycleOwner = this@IncenseSelectFragment
            }

        mainBinding.viewIncensePlaytimePicker.apply {
            vm = playtimePickerViewModel
            lifecycleOwner = this@IncenseSelectFragment
        }

        mainBinding.rvIncensesRecommendation.apply {
            val snapHelper = IncenseRecyclerViewSnapHelper(this)
            val incenseAdapter = IncenseRecyclerViewAdapter(viewLifecycleOwner) {
                mainBinding.indicatorIncensesRecommendation.attachToRecyclerView(this, snapHelper)
            }
            val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            val scrollListener = IncenseRecyclerViewScrollListener(linearLayoutManager, incenseAdapter, 2) {
                incenseSelectViewModel.changeSelectedIncense(it)
            }

            adapter = incenseAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(IncenseRecyclerViewDecoration())
            addOnScrollListener(scrollListener)
            snapHelper.attachToRecyclerView(this)
        }

        incenseSelectViewModel.incenseSelectToastMessageEvent.observe(viewLifecycleOwner, EventObserver {
            sharedViewModel.showToastMessage(it)
        })

        incenseSelectViewModel.backClickEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })

        incenseSelectViewModel.openMeditationEvent.observe(viewLifecycleOwner, EventObserver {
            goMeditation()
        })

        playtimePickerViewModel.playtimePickEvent.observe(viewLifecycleOwner, EventObserver {
            incenseSelectViewModel.setPlaytime(it)
        })
    }

    private fun goMeditation() {
        findNavController().navigate(R.id.action_incenseSelectFragment_to_meditationFragment)
    }
}