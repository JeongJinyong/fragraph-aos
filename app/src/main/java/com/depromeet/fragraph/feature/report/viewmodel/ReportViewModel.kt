package com.depromeet.fragraph.feature.report.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.domain.repository.UserRepository

class ReportViewModel@ViewModelInject constructor(
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _days = MutableLiveData(listOf("01", "02", "03", "04", "05", "06", "07"))
    val days: LiveData<List<String>>
        get() = _days

    private val _playTimes = MutableLiveData(listOf(30f, 300f, 400f, 200f, 500f, 430f, 120f))
    val playTimes: LiveData<List<Float>>
        get() = _playTimes
}