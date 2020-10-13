package com.depromeet.fragraph.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.depromeet.fragraph.R
import com.depromeet.fragraph.databinding.ActivityMainBinding
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