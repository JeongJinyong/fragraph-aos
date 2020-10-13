package com.depromeet.fragraph.feature.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.MainActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        kakao.setOnClickListener {
//            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//                if (error != null) {
//                    Timber.tag(MainActivity.TAG).e(error, "로그인 실패")
//                }
//                else if (token != null) {
//                    Timber.tag(MainActivity.TAG).i( "로그인 성공 ${token.accessToken}")
//                }
//            }
//
//            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//            if (LoginClient.instance.isKakaoTalkLoginAvailable(this@MainActivity)) {
//                LoginClient.instance.loginWithKakaoTalk(this@MainActivity, callback = callback)
//            } else {
//                LoginClient.instance.loginWithKakaoAccount(this@MainActivity, callback = callback)
//            }
//        }
    }

    companion object {
        const val TAG = "SignInFragment"
    }
}