package com.depromeet.fragraph.feature.splash.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _openAppEvent = MutableLiveData<Event<String>>()
    val openAppEvent: LiveData<Event<String>>
        get() = _openAppEvent

    init {
        accessTokenTest()
    }

    fun accessTokenTest() {
        viewModelScope.launch {
            val token = authRepository.getAccessToken()
                .first()
            Timber.tag(TAG).e("get accessToken: ${token ?: "null"}")
        }
    }

    companion object {
        const val TAG = "SplashViewModel"
    }
}