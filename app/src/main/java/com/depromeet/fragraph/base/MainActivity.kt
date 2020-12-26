package com.depromeet.fragraph.base

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.KEY_AUTH_TOKEN
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.core.ext.authSharedPreferences
import com.depromeet.fragraph.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    @Inject
    lateinit var toast: Toast

    lateinit var binding: ActivityMainBinding
    lateinit var toastView: View
    lateinit var toastTextView: TextView

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.root_nav_host_fragment)
    }

    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fixme 삭제 !!
        // 만료된 sample 토큰: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMiIsImlzcyI6ImFjY291bnRzLmRlcHJvbWVldC5jb20iLCJpYXQiOjE2MDM0MzgxMDYsImV4cCI6MTYwNDA0MjkwNn0.f_Kv78-NK9Ec1oQ2UYCgj3Me9I__M5XO5nTVEZpIQJE
//        Timber.tag(TAG).i("token: ${this.authSharedPreferences().getString(KEY_AUTH_TOKEN, null)}")
//        this.authSharedPreferences().edit().clear().commit()

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        initNavigation()

        toastView = layoutInflater.inflate(R.layout.view_toast, findViewById(R.id.fl_toast_container))
        toastTextView = toastView.findViewById(R.id.tv_toast_message)

        sharedViewModel.showToastMessageEvent.observe(this, EventObserver {stringRes ->
            if (stringRes != R.string.app_name) {
                toastTextView.text = resources.getString(stringRes)
                toast.view = toastView
                toast.show()
            }
        })
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