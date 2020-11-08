package com.depromeet.fragraph.feature.recommendation.feeling_select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.FragmentFeelingSelectBinding
import com.depromeet.fragraph.feature.recommendation.feeling_select.adapter.FeelingRecyclerViewAdapter
import com.depromeet.fragraph.feature.recommendation.feeling_select.viewmodel.FeelingSelectViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeelingSelectFragment: Fragment(R.layout.fragment_feeling_select) {

    private val feelingSelectViewModel: FeelingSelectViewModel by viewModels()

    private val feelingRecyclerViewAdapter: FeelingRecyclerViewAdapter by lazy {
        FeelingRecyclerViewAdapter(this@FeelingSelectFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentFeelingSelectBinding.bind(view)
            .apply {
                vm = feelingSelectViewModel
                lifecycleOwner = this@FeelingSelectFragment
            }

        binding.rvFeelingTag.apply {
            adapter = feelingRecyclerViewAdapter
            layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW)
        }
    }
}