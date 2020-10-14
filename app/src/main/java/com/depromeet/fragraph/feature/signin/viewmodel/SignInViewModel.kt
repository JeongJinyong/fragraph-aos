package com.depromeet.fragraph.feature.signin.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.repository.AuthRepository
import com.depromeet.fragraph.feature.signin.SignInFragment
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _kakaoOpened = MutableLiveData<Event<Unit>>()
    val kakaoOpened: LiveData<Event<Unit>>
        get() = _kakaoOpened

    fun openKakao() {
        Timber.tag(SignInFragment.TAG).d("kakao 로그인 클릭")
        _kakaoOpened.value = Event(Unit)
    }

    fun signInByKakao(kakaoToken: String) {
        viewModelScope.launch {
            Timber.tag(TAG).i("로그인 성공, token: $kakaoToken")
        }
    }

    companion object {
        const val TAG = "SignInViewModel"
    }
}