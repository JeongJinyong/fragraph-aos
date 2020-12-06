package com.depromeet.fragraph.feature.report.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.core.ext.*
import com.depromeet.fragraph.domain.repository.HistoryRepository
import com.depromeet.fragraph.domain.repository.ReportRepository
import com.depromeet.fragraph.feature.report.model.HistoryUiModel
import com.depromeet.fragraph.feature.report.model.ReportUiModel
import com.depromeet.fragraph.feature.report.model.getDefaultReportUiModel
import com.depromeet.fragraph.feature.report.model.getEmptyUiModel
import com.depromeet.fragraph.feature.signin.SignInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class ReportViewModel @ViewModelInject constructor(
    private val reportRepository: ReportRepository,
    private val historyRepository: HistoryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _openRecommendationEvent = MutableLiveData<Event<Unit>>()
    val openRecommendationEvent: LiveData<Event<Unit>>
        get() = _openRecommendationEvent

    private val _reportModel = MutableLiveData(getDefaultReportUiModel())
    val reportModel: MutableLiveData<ReportUiModel>
        get() = _reportModel

    private val _historyCalendarVisible = MutableLiveData(false)
    val historyCalendarVisible: LiveData<Boolean>
        get() = _historyCalendarVisible

    private val _month = MutableLiveData(System.currentTimeMillis().miliSecondsToMonth())
    val month: MutableLiveData<String>
        get() = _month

    private val _histories = MutableLiveData<List<HistoryUiModel>>(listOf())
    val histories: MutableLiveData<List<HistoryUiModel>>
        get() = _histories

    fun refreshData() {
        val today = LocalDate.now()
        getReport()
        getHistories(today.year, today.monthValue, today.dayOfMonth)
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

    fun getHistories(year: Int, month: Int, day: Int) {
        _month.postValue(month.toString())

        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.getHistories(year, month)
                .catch {
                    Timber.tag(SignInFragment.TAG).e("히스토리 가져오는 중 오류 발생")
                }
                .map { histories->

                    val centerPosition = histories.indexOfFirst {
                        it.createdAt.miliSecondsToDay().toInt() == day
                    }.let {
                        if (it < 0) 0 else it
                    }

                    histories.filter { it.keywords.size > 2 }
                        .mapIndexed { index, history ->
                            HistoryUiModel(history.id, history.createdAt, "${history.playTime/60}분 재생", history.incense.title, history.memo,
                                history.keywords[0].name, history.keywords[1].name, history.keywords[2].name,
                                isExisted = true, isBack = false,
                                isCenter = MutableLiveData(index == centerPosition))
                        }
                }
                .map {
                    val historyUiModels = mutableListOf<HistoryUiModel>()
                    for( i in 1 until (getLastDayOfMonth(year, month, day).toInt() + 1)) {
                        it.firstOrNull { history -> history.createdAt.miliSecondsToDay().toInt() == i }?.let {history ->
                            historyUiModels.add(history)
                        } ?: run {
                            if (day == i) {
                                historyUiModels.add(getEmptyUiModel(getMiliSeconds(year, month, i), true))
                            } else {
                                historyUiModels.add(getEmptyUiModel(getMiliSeconds(year, month, i), false))
                            }
                        }
                    }
                    historyUiModels
                }
                .collect {
                    histories.postValue(it)
                }
        }
    }

    fun onCalendarClick(year: Int, month: Int, day: Int) {
        _historyCalendarVisible.postValue(false)
        getHistories(year, month, day)
    }

    fun onHistoryCalendarBackgroundClick() {
        _historyCalendarVisible.postValue(false)
    }

    fun openHistoryCalendar() {
        _historyCalendarVisible.postValue(true)
    }

    fun startRecommendation() {
        _openRecommendationEvent.postValue(Event(Unit))
    }
}