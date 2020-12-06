package com.depromeet.fragraph.base

import androidx.annotation.StringRes
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.Event

class SharedViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _showToastMessageEvent = MutableLiveData(Event(R.string.app_name))
    val showToastMessageEvent: MutableLiveData<Event<Int>>
        get() = _showToastMessageEvent

    fun showToastMessage(@StringRes resId: Int) {
        return _showToastMessageEvent.postValue(Event(resId))
    }
}