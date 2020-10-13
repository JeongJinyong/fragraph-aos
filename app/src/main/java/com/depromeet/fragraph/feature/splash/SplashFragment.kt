package com.depromeet.fragraph.feature.splash

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.FragmentSplashBinding
import com.depromeet.fragraph.domain.model.PageType
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
        val binding = FragmentSplashBinding.bind(view)

        binding.lottieViewSplash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                // animation 을 한번은 실행시키기 위해 호출
                splashViewModel.finishAnimation()
                binding.lottieViewSplash.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        splashViewModel.openAppEvent.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {event ->
                if(event.isFinishedAnimation) {
                    when(event.openPageType) {
                        PageType.SIGNIN -> goSignIn()
                        PageType.REPORT -> Timber.tag(TAG).e("This page not implementation")
                    }
                }
            }
        })
    }

    private fun goSignIn() {
        findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
    }

    private fun goReport() {
        findNavController().navigate(R.id.action_splashFragment_to_reportFragment)
    }

    companion object {
        const val TAG = "SplashFragment"
    }
}