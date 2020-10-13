package com.depromeet.fragraph.feature.splash.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.model.PageType
import com.depromeet.fragraph.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var isLottieFinished: Boolean = false
    private var openViewType: PageType = PageType.SPLASH

    data class OpenAppEvent(
        val isLottieFinished: Boolean,
        val openPageType: PageType,
    )
    private val _openAppEvent = MutableLiveData<Event<OpenAppEvent>>()
    val openAppEvent: LiveData<Event<OpenAppEvent>>
        get() = _openAppEvent

    init {
        checkAccessToken()
    }

    fun finishAnimation() {
        isLottieFinished = true
        _openAppEvent.postValue(Event(OpenAppEvent(isLottieFinished, openViewType)))
    }

    private fun checkAccessToken() {
        authRepository.getAccessToken()?.let {
            openViewType = PageType.REPORT
            _openAppEvent.postValue(Event(OpenAppEvent(isLottieFinished, openViewType)))
        } ?: kotlin.run {
            openViewType = PageType.SIGNIN
            _openAppEvent.postValue(Event(OpenAppEvent(isLottieFinished, openViewType)))
        }
    }

    companion object {
        const val TAG = "SplashViewModel"
    }
}