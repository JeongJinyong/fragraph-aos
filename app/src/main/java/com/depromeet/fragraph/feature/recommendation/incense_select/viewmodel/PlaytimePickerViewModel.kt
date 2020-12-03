package com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.core.event.Event

class PlaytimePickerViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _playtimePickEvent = MutableLiveData<Event<Int>>()
    val playtimePickEvent: LiveData<Event<Int>>
        get() = _playtimePickEvent

    private val _minutes = MutableLiveData("0")
    val minutes: LiveData<String>
        get() =  _minutes

    private val _seconds = MutableLiveData("0")
    val seconds: LiveData<String>
        get() =  _seconds


    fun onCompleteClick() {
        val totalSeconds = minutes.value!!.toInt() * 60 + seconds.value!!.toInt()
        _playtimePickEvent.postValue(Event(totalSeconds))
    }

    fun setMinutes(min: Int) {
        _minutes.value = min.toString()
    }

    fun setSeconds(sec: Int) {
        _seconds.value = sec.toString()
    }
}