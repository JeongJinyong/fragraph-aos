package com.depromeet.fragraph.feature.home.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.core.ext.getLastDayOfMonth
import com.depromeet.fragraph.core.ext.getMilliSeconds
import com.depromeet.fragraph.core.ext.milliSecondsToDay
import com.depromeet.fragraph.core.ext.milliSecondsToMonth
import com.depromeet.fragraph.domain.repository.HistoryRepository
import com.depromeet.fragraph.domain.repository.ReportRepository
import com.depromeet.fragraph.feature.home.model.HistoryUiModel
import com.depromeet.fragraph.feature.home.model.ReportUiModel
import com.depromeet.fragraph.feature.home.model.getDefaultReportUiModel
import com.depromeet.fragraph.feature.home.model.getEmptyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class HomeViewModel @ViewModelInject constructor(
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

    private val _month = MutableLiveData(System.currentTimeMillis().milliSecondsToMonth())
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
                .catch {e->
                    Timber.tag(TAG).e(e,"레포트 가져오는 중 오류 발생")
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
                    Timber.tag(TAG).e("히스토리 가져오는 중 오류 발생")
                }
                .map { histories->
                    val centerPosition = histories.indexOfFirst {
                        it.createdAt.milliSecondsToDay().toInt() == day
                    }.let {
                        if (it < 0) 0 else it
                    }

                    histories.filter { it.keywords.isNotEmpty()  }
                        .mapIndexed { index, history ->
                            HistoryUiModel(history.id, history.createdAt, "${history.playTime/60}분 재생", history.incense.title, history.memo,
                                history.keywords[0].name,
                                if(history.keywords.size > 1) history.keywords[1].name else null,
                                if(history.keywords.size > 2) history.keywords[2].name else null,
                                isExisted = true,
                                isBack = false,
                                isCenter = MutableLiveData(index == centerPosition))
                        }
                }
                .map {
                    val historyUiModels = mutableListOf<HistoryUiModel>()
                    for( i in 1 until (getLastDayOfMonth(year, month, day) + 1)) {
                        val addHistories = it.filter { history -> history.createdAt.milliSecondsToDay().toInt() == i }
                        Timber.d("addHistories: $addHistories")
                        if (addHistories.isEmpty()) {
                            if (day == i) {
                                historyUiModels.add(getEmptyUiModel(getMilliSeconds(year, month, i), true))
                            } else {
                                historyUiModels.add(getEmptyUiModel(getMilliSeconds(year, month, i), false))
                            }
                        } else {
                            historyUiModels.addAll(addHistories)
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

    companion object {
        const val TAG = "ReportViewModel"
    }
}