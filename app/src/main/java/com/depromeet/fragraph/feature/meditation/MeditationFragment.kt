package com.depromeet.fragraph.feature.meditation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.FragmentMeditationBinding
import com.depromeet.fragraph.feature.meditation.viewmodel.MeditationViewModel

class MeditationFragment: Fragment(R.layout.fragment_meditation) {

    private val meditationViewModel: MeditationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMeditationBinding.bind(view)
            .apply {
                vm = meditationViewModel
                lifecycleOwner = this@MeditationFragment
            }
    }
}