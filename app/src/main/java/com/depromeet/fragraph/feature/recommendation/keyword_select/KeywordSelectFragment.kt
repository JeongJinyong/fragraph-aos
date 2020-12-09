package com.depromeet.fragraph.feature.recommendation.keyword_select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.SharedViewModel
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.core.ext.toast
import com.depromeet.fragraph.databinding.FragmentKeywordSelectBinding
import com.depromeet.fragraph.feature.recommendation.keyword_select.adapter.KeywordRecyclerViewAdapter
import com.depromeet.fragraph.feature.recommendation.keyword_select.viewmodel.KeywordSelectViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import timber.log.Timber

@AndroidEntryPoint
class KeywordSelectFragment: Fragment(R.layout.fragment_keyword_select) {

    private val keywordSelectViewModel: KeywordSelectViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentKeywordSelectBinding.bind(view)
            .apply {
                vm = keywordSelectViewModel
                lifecycleOwner = this@KeywordSelectFragment
            }

        binding.rvKeywords.apply {
            adapter = KeywordRecyclerViewAdapter(viewLifecycleOwner) {
                    keywordSelectViewModel.keywordClick(it)
            }
            layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW)
        }

        keywordSelectViewModel.keywordToastMessageEvent.observe(viewLifecycleOwner, EventObserver {
            sharedViewModel.showToastMessage(it)
        })

        keywordSelectViewModel.openIncenseSelectEvent.observe(viewLifecycleOwner, EventObserver {
            goIncenseSelect()
        })

        keywordSelectViewModel.backClickEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })
    }

    private fun goIncenseSelect() {
        findNavController().navigate(R.id.action_keywordSelectFragment_to_incenseSelectFragment)
    }
}