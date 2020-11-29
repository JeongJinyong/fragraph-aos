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

    private val _month = MutableLiveData(System.currentTimeMillis().miliSecondsToMonth())
    val month: MutableLiveData<String>
        get() = _month

    private val _histories = MutableLiveData<List<HistoryUiModel>>(listOf())
    val histories: MutableLiveData<List<HistoryUiModel>>
        get() = _histories

    init {
        getReport()
        getHistories(System.currentTimeMillis().miliSecondsToMonth(), addAfter = true)
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

    fun getHistories(month: String, addAfter: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.getHistories(month)
                .catch {
                    Timber.tag(SignInFragment.TAG).e("히스토리 가져오는 중 오류 발생")
                }
                .map { histories->
                    histories.filter { it.keywords.size > 2 }
                        .map {
                            HistoryUiModel(it.id, it.createdAt, "${it.playTime/60}분 재생", it.incense.title, it.memo,
                                it.keywords[0].name, it.keywords[1].name, it.keywords[2].name,
                                isExisted = true, isBack = false,
                                isCenter = MutableLiveData(it.createdAt.miliSecondsToStringFormat(
                                    FRAGRAPH_HISTORY_FORMAT
                                ) == System.currentTimeMillis().miliSecondsToStringFormat(
                                    FRAGRAPH_HISTORY_FORMAT
                                )))
                        }
                }
                .map {
                    val historyUiModels = mutableListOf<HistoryUiModel>()
                    for( i in 1 until (getLastDayOfMonth().toInt() + 1)) {
                        it.firstOrNull { history -> history.createdAt.miliSecondsToDay().toInt() == i }?.let {history ->
                            historyUiModels.add(history)
                        } ?: run { historyUiModels.add(getEmptyUiModel(getMiliSecondsForDate(i))) }
                    }
                    historyUiModels
                }
                .map {
                    val newHistories = mutableListOf<HistoryUiModel>()
                    if (addAfter) {
                        newHistories.addAll(histories.value!!)
                        newHistories.addAll(it)
                    } else {
                        newHistories.addAll(it)
                        newHistories.addAll(histories.value!!)
                    }
                    newHistories
                }
                .collect {
                    histories.postValue(it)
                }
        }
    }




    fun startRecommendation() {
        _openRecommendationEvent.postValue(Event(Unit))
    }
}