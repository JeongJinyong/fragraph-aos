package com.depromeet.fragraph.feature.report.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Memo
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.ReportRepository
import com.depromeet.fragraph.feature.report.model.HistoryUiModel
import com.depromeet.fragraph.feature.report.model.ReportUiModel
import com.depromeet.fragraph.feature.signin.SignInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class ReportViewModel @ViewModelInject constructor(
    private val reportRepository: ReportRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _openRecommendationEvent = MutableLiveData<Event<Unit>>()
    val openRecommendationEvent: LiveData<Event<Unit>>
        get() = _openRecommendationEvent

    private val _reportModel = MutableLiveData<ReportUiModel>()
    val reportModel: MutableLiveData<ReportUiModel>
        get() = _reportModel

    private val _days = MutableLiveData(listOf("lavender", "pepper mint", "03", "04", "05", "06", "07"))
    val days: LiveData<List<String>>
        get() = _days

    private val _playTimes = MutableLiveData(listOf(30f, 300f, 400f, 200f, 500f, 430f, 120f))
    val playTimes: LiveData<List<Float>>
        get() = _playTimes

    val incense = Incense(1, IncenseTitle.LAVENDER, "어쩌구 저쩌구", "이미지라네")
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

    init {
        getReport()
    }

    private fun getReport() {
        viewModelScope.launch(Dispatchers.IO) {
            reportRepository.getReport()
                .catch {
                    Timber.tag(SignInFragment.TAG).e("레포트 가져오는 중 오류 발생")
                }
                .map {
                    val playCount = it.counts.reduce { acc, count -> acc + count }
                    ReportUiModel(playCount.toInt().toString(), it.titles, it.counts)
                }
                .collect { _reportModel.postValue(it) }
        }
    }


    fun startRecommendation() {
        _openRecommendationEvent.postValue(Event(Unit))
    }
}