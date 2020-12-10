package com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.Event

class PlaytimePickerViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _playtimePickEvent = MutableLiveData<Event<Int>>()
    val playtimePickEvent: LiveData<Event<Int>>
        get() = _playtimePickEvent

    private val _playtimePickerToastMessageEvent = MutableLiveData<Event<Int>>()
    val playtimePickerToastMessageEvent: LiveData<Event<Int>>
        get() = _playtimePickerToastMessageEvent

    private val _minutes = MutableLiveData("0")
    val minutes: LiveData<String>
        get() =  _minutes

    private val _seconds = MutableLiveData("0")
    val seconds: LiveData<String>
        get() =  _seconds

    private val _hasPlaytime = MutableLiveData(false)
    val hasPlaytime: LiveData<Boolean>
        get() =  _hasPlaytime


    fun onCompleteClick() {
        if (hasPlaytime.value!!) {
            val totalSeconds = minutes.value!!.toInt() * 60 + seconds.value!!.toInt()
            _playtimePickEvent.postValue(Event(totalSeconds))
        } else {
            _playtimePickerToastMessageEvent.postValue(Event(R.string.incense_select_playtime_less_than_0))
        }
    }

    fun setMinutes(min: Int) {
        _minutes.value = min.toString()
        setHasPlaytime(min, seconds.value!!.toInt())
    }

    fun setSeconds(sec: Int) {
        _seconds.value = sec.toString()
        setHasPlaytime(minutes.value!!.toInt(), sec)
    }

    private fun setHasPlaytime(min: Int, sec: Int) {
        _hasPlaytime.postValue(min > 0 || sec > 0)
    }
}