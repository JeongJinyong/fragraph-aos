package com.depromeet.fragraph.feature.signin.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.core.ext.combine
import com.depromeet.fragraph.domain.model.PageType
import com.depromeet.fragraph.domain.repository.AuthRepository
import com.depromeet.fragraph.domain.repository.UserRepository
import com.depromeet.fragraph.feature.signin.SignInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class KakaoSignInViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isProgressed = MutableLiveData(false)
    val isProgressed: LiveData<Boolean>
        get() = _isProgressed

    private val _kakaoOpened = MutableLiveData<Event<Unit>>()
    val kakaoOpened: LiveData<Event<Unit>>
        get() = _kakaoOpened

    private val _openMainEvent = MutableLiveData<Event<Unit>>()
    val openMainEvent: LiveData<Event<Unit>>
        get() = _openMainEvent

    fun openKakao() {
        Timber.d("kakao 로그인 클릭")
        _kakaoOpened.value = Event(Unit)
    }

    fun signInByKakao(kakaoToken: String) {
        _isProgressed.value = true

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.loginWithKakao(kakaoToken)
                .flatMapConcat { userRepository.getMyInfo() }
                .catch {
                    Timber.e("오류 발생")
                }
                .collect {
                    Timber.i("로그인 성공, user: $it.")
                    _isProgressed.postValue(false)
                    _openMainEvent.postValue(Event(Unit))
                }
        }
    }
}