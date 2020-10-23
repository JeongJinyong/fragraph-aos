package com.depromeet.fragraph.feature.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.ext.toast
import com.depromeet.fragraph.databinding.FragmentSignInBinding
import com.depromeet.fragraph.databinding.FragmentSplashBinding
import com.depromeet.fragraph.feature.signin.viewmodel.SignInViewModel
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

//    private lateinit var binding: FragmentSignInBinding
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSignInBinding
            .bind(view).apply {
                lifecycleOwner = this@SignInFragment
                vm = signInViewModel
            }

        signInViewModel.kakaoOpened.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Timber.tag(TAG).e(error, "로그인 실패")
                        requireContext().toast("로그인 실패")
                    } else if (token != null) {
                        signInViewModel.signInByKakao(token.accessToken)
                    }
                }

                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (LoginClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                    LoginClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
                } else {
                    LoginClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                }
            }
        }

        signInViewModel.openMainEvent.observe(viewLifecycleOwner) {event ->
            event.getContentIfNotHandled()?.let {
                goReport()
            }
        }

    }

    private fun goReport() {
        findNavController().navigate(R.id.action_signInFragment_to_reportFragment)
    }

    companion object {
        const val TAG = "SignInFragment"
    }
}