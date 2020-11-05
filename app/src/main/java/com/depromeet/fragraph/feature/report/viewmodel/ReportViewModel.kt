package com.depromeet.fragraph.feature.report.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.domain.model.History
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Memo
import com.depromeet.fragraph.domain.repository.UserRepository
import com.depromeet.fragraph.feature.report.model.HistoryUiModel

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

    val incense = Incense(1, "Lavendar", "어쩌구 저쩌구", "이미지라네")
    val memo = Memo(1, "삼겹살 먹어서 기분 짱좋음")
    val histories = MutableLiveData<List<HistoryUiModel>>(
        listOf(
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
            HistoryUiModel(1, 1603894614000, 300, incense, memo, false),
        )
    )
}