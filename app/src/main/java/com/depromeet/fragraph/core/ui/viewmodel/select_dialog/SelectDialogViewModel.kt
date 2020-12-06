package com.depromeet.fragraph.core.ui.viewmodel.select_dialog

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.Event

class SelectDialogViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _onBtcClickEvent = MutableLiveData<Event<Int>>()
    val onBtcClickEvent: LiveData<Event<Int>>
        get() = _onBtcClickEvent

    private val _selectDialogType = MutableLiveData<SelectDialogType>(SelectDialogType.MEMO_ON_WRITING)
    val selectDialogType: LiveData<SelectDialogType>
        get() = _selectDialogType

    fun setDialogType(
        dialogType: SelectDialogType,
    ) {
        _selectDialogType.postValue(dialogType)
    }

    fun onClickLeft() {
        _onBtcClickEvent.postValue(Event(selectDialogType.value!!.leftFlag))
    }

    fun onClickRight() {
        _onBtcClickEvent.postValue(Event(selectDialogType.value!!.rightFlag))
    }
}