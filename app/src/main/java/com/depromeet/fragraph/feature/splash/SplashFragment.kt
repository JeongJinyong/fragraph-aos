package com.depromeet.fragraph.feature.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.FragmentSplashBinding
import com.depromeet.fragraph.feature.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(TAG).d("SplashFragment init !!!!")
        val binding = FragmentSplashBinding.bind(view)

        binding.sampleBtn.setOnClickListener { goSignIn() }

        splashViewModel.openAppEvent.observe(viewLifecycleOwner, {event ->
            event.getContentIfNotHandled()?.let {
                when(it) {
                    OPEN_SIGNIN -> goSignIn()
                }
            }
        })
    }

    private fun goSignIn() {
        findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
    }

    companion object {
        const val TAG = "SplashFragment"

        const val NOT_OPEN = "NOT_OPEN"
        const val OPEN_SIGNIN = "OPEN_SIGNIN"
        const val OPEN_MAIN = "OPEN_MAIN"
    }
}