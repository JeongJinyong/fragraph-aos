package com.depromeet.fragraph.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.KEY_AUTH_TOKEN
import com.depromeet.fragraph.core.ext.authSharedPreferences
import com.depromeet.fragraph.databinding.ActivityMainBinding
import com.depromeet.fragraph.feature.signin.viewmodel.SignInViewModel
import com.depromeet.fragraph.feature.splash.viewmodel.SplashViewModel
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.root_nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fixme 삭제 !!
        // 만료된 sample 토큰: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMiIsImlzcyI6ImFjY291bnRzLmRlcHJvbWVldC5jb20iLCJpYXQiOjE2MDM0MzgxMDYsImV4cCI6MTYwNDA0MjkwNn0.f_Kv78-NK9Ec1oQ2UYCgj3Me9I__M5XO5nTVEZpIQJE
        Timber.tag(TAG).i("token: ${this.authSharedPreferences().getString(KEY_AUTH_TOKEN, null)}")
//        this.authSharedPreferences().edit().clear().commit();

        binding
        initNavigation()
    }

    private fun initNavigation() {

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.tag(TAG).d("destinationId: ${destination.id}, destinationLabel: ${destination.label}")
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}