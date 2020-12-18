package com.depromeet.fragraph.feature.signin

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.SharedViewModel
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentSignInBinding
import com.depromeet.fragraph.feature.signin.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val signInViewModel: SignInViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    @Inject
    lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var binding: FragmentSignInBinding

    private val requestGoogleSignIn = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->

        runCatching {
            GoogleSignIn.getSignedInAccountFromIntent(activityResult.data).result
        }.map {
            it ?: kotlin.run { throw Exception("account 가져오는데 에러") }
        }.map {
            it.idToken ?: kotlin.run { throw Exception("google idToken 가져오는데 에러") }
        }.onSuccess {
            signInViewModel.signInByGoogle(it)
        }.onFailure {
            Timber.e(it, "fail to google login ")
            sharedViewModel.showToastMessage(R.string.signIn_google_error)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignInBinding
            .bind(view).apply {
                lifecycleOwner = this@SignInFragment
                vm = signInViewModel
            }

        signInViewModel.kakaoOpened.observe(viewLifecycleOwner, EventObserver { event ->
            val kakaoCallbackWeb: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Timber.tag(TAG).e(error, "카카오 로그인 실패")
                    sharedViewModel.showToastMessage(R.string.signIn_kakao_error)
                } else if (token != null) {
                    signInViewModel.signInByKakao(token.accessToken)
                }
            }

            val kakaoCallbackApp: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Timber.tag(TAG).e(error, "카카오 로그인 실패")
                    if (error.message == "KakaoTalk is installed but not connected to Kakao account.") {
                        // 카카오톡은 있는데 로그인 안한 경우는 웹으로 이동시킴
                        LoginClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallbackWeb)
                    } else {
                        sharedViewModel.showToastMessage(R.string.signIn_kakao_error)
                    }
                } else if (token != null) {
                    signInViewModel.signInByKakao(token.accessToken)
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (LoginClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                LoginClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoCallbackApp)
            } else {
                LoginClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallbackWeb)
            }
        })

        signInViewModel.googleSignInOpened.observe(viewLifecycleOwner, EventObserver { event ->
            val signInIntent = mGoogleSignInClient.signInIntent
            requestGoogleSignIn.launch(signInIntent)
        })

        signInViewModel.openMainEvent.observe(viewLifecycleOwner, EventObserver { event ->
            goReport()
        })

    }

    private fun goReport() {
        findNavController().navigate(R.id.action_signInFragment_to_reportFragment)
    }

    companion object {
        const val TAG = "SignInFragment"
//        const val RC_SIGN_IN = 999
    }
}