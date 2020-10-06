package com.depromeet.fragraph.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.depromeet.fragraph.R
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kakao.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Timber.tag(TAG).e(error, "로그인 실패")
                }
                else if (token != null) {
                    Timber.tag(TAG).i( "로그인 성공 ${token.accessToken}")
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this@MainActivity)) {
                LoginClient.instance.loginWithKakaoTalk(this@MainActivity, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this@MainActivity, callback = callback)
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}