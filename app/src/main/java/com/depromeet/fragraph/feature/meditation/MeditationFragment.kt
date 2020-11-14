package com.depromeet.fragraph.feature.meditation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.player.Player
import com.depromeet.fragraph.databinding.FragmentMeditationBinding
import com.depromeet.fragraph.feature.meditation.viewmodel.MeditationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MeditationFragment: Fragment(R.layout.fragment_meditation) {

    private val meditationViewModel: MeditationViewModel by viewModels()

    @Inject
    lateinit var player: Player

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMeditationBinding.bind(view)
            .apply {
                vm = meditationViewModel
                lifecycleOwner = this@MeditationFragment
            }

        meditationViewModel.musicUrl.observe(viewLifecycleOwner) {url ->
            player.setPlayer(url) {
                meditationViewModel.initPlayTime(player.duration(), player.remainingTime()) // ms 로 체크됨
                startPlayer()
            }
        }
    }

    private fun startPlayer() {
        player.start()
        viewLifecycleOwner.lifecycleScope.launch {
            player.remainingTimeFlow()
                .collect { meditationViewModel.setRemainingTime(it) }
        }
    }

    override fun onDetach() {
        player.releasePlayer()
        super.onDetach()
    }

    override fun onDestroy() {
        player.releasePlayer()
        super.onDestroy()
    }

    companion object {
        const val TAG = "MeditationFragment"
    }
}