package com.depromeet.fragraph.feature.signin.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.repository.AuthRepository
import com.depromeet.fragraph.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import timber.log.Timber

class GoogleSignInViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _googleSignInOpened = MutableLiveData<Event<Unit>>()
    val googleSignInOpened: LiveData<Event<Unit>>
        get() = _googleSignInOpened

    private val _openMainEvent = MutableLiveData<Event<Unit>>()
    val openMainEvent: LiveData<Event<Unit>>
        get() = _openMainEvent

    private val _isProgressed = MutableLiveData(false)
    val isProgressed: LiveData<Boolean>
        get() = _isProgressed

    fun openGoogleSignIn() {
        Timber.d("google 로그인 클릭")
        _googleSignInOpened.value = Event(Unit)
    }

    fun signInByGoogle(googleToken: String) {
        Timber.d("구글 로그인 !!! token : $googleToken")
        _isProgressed.value = true

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.loginWithGoogle(googleToken)
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