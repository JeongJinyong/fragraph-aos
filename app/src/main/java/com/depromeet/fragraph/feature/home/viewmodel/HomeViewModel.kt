package com.depromeet.fragraph.feature.home.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.core.ext.*
import com.depromeet.fragraph.core.ext.milliSecondsToMonth
import com.depromeet.fragraph.core.ui.select_dialog.SelectDialogType
import com.depromeet.fragraph.domain.model.Memo
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

    private val _openSelectDialogEvent = MutableLiveData<Event<SelectDialogType>>()
    val openSelectDialogEvent: LiveData<Event<SelectDialogType>>
        get() = _openSelectDialogEvent

    private val _openMemoDialogEvent = MutableLiveData<Event<Triple<Int, Long, Memo>>>()
    val openMemoDialogEvent: LiveData<Event<Triple<Int, Long, Memo>>>
        get() = _openMemoDialogEvent

    private val _homeViewModelToastEvent = MutableLiveData<Event<Int>>()
    val homeViewModelToastEvent: LiveData<Event<Int>>
        get() = _homeViewModelToastEvent

    private val _reportModel = MutableLiveData(getDefaultReportUiModel())
    val reportModel: MutableLiveData<ReportUiModel>
        get() = _reportModel

    private val _editHistory = MutableLiveData<HistoryUiModel>()
    val editHistory: LiveData<HistoryUiModel>
        get() = _editHistory

    private val _historyCalendarVisible = MutableLiveData(false)
    val historyCalendarVisible: LiveData<Boolean>
        get() = _historyCalendarVisible

    private val _moreDialogVisible = MutableLiveData(false)
    val moreDialogVisible: LiveData<Boolean>
        get() = _moreDialogVisible

    private val _selectDialogVisible = MutableLiveData(false)
    val selectDialogVisible: LiveData<Boolean>
        get() = _selectDialogVisible

    private val _memoDialogVisible = MutableLiveData(false)
    val memoDialogVisible: LiveData<Boolean>
        get() = _memoDialogVisible

    private val _month = MutableLiveData(System.currentTimeMillis().milliSecondsToMonth())
    val month: MutableLiveData<String>
        get() = _month

    private val _histories = MutableLiveData<List<HistoryUiModel>>(listOf())
    val histories: MutableLiveData<List<HistoryUiModel>>
        get() = _histories

    private fun loadReport() {
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

    fun loadHistories(year: Int, month: Int, day: Int) {
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

                    histories.filter { it.selectedKeywords.isNotEmpty()  }
                        .mapIndexed { index, history ->
                            HistoryUiModel(history.id, history.createdAt, "${history.playTime/60}분 재생", history.incense.title, history.memo,
                                history.selectedKeywords,
                                history.unselectedKeywords,
//                                history.keywords[0].name,
//                                if(history.keywords.size > 1) history.keywords[1].name else null,
//                                if(history.keywords.size > 2) history.keywords[2].name else null,
                                isExisted = true,
                                isBack = false,
                                isCenter = MutableLiveData(index == centerPosition)) { editHistory ->

                                _moreDialogVisible.postValue(true)
                                _editHistory.postValue(editHistory)
                            }
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

    fun refreshToday() {
        val today = LocalDate.now()
        loadReport()
        loadHistories(today.year, today.monthValue, today.dayOfMonth)
    }

    fun refreshEditedData() {
        editHistory.value?.let {
            val year = it.createdAt.milliSecondsToYear()
            val month = it.createdAt.milliSecondsToMonth()
            val day = it.createdAt.milliSecondsToDay()
            loadHistories(year.toInt(), month.toInt(), day.toInt())
        }
    }

    fun refreshCalendarData(year: Int, month: Int, day: Int) {
        hideAllBackground()
        loadHistories(year, month, day)
    }

    fun deleteHistory() {
        hideAllBackground()
        editHistory.value?.let {history ->
            viewModelScope.launch {
                historyRepository.deleteHistory(history.id)
                    .catch {
                        Timber.e("삭제중 오류 발생")
                        _homeViewModelToastEvent.postValue(Event(R.string.home_toast_delete_failure))
                    }
                    .collect {
                        Timber.d("삭제 완료")
                        val year = history.createdAt.milliSecondsToYear()
                        val month = history.createdAt.milliSecondsToMonth()
                        val day = history.createdAt.milliSecondsToDay()
                        loadReport()
                        loadHistories(year.toInt(), month.toInt(), day.toInt())
                        _homeViewModelToastEvent.postValue(Event(R.string.home_toast_delete_success))
                    }
            }
        }
    }

    fun openHistoryCalendar() {
        hideAllBackground()
        _historyCalendarVisible.postValue(true)
    }

    fun hideCalendar() {
        _historyCalendarVisible.postValue(false)
    }

    fun hideEditDialog() {
        _moreDialogVisible.postValue(false)
    }

    fun hideSelectDialog() {
        _selectDialogVisible.postValue(false)
    }

    fun openMemoDialog() {
        hideAllBackground()
        editHistory.value?.let {history->
            history.memo?.let {
                _memoDialogVisible.postValue(true)
                _openMemoDialogEvent.postValue(Event(Triple(history.id, history.createdAt, it)))
            } ?: run {
                _homeViewModelToastEvent.postValue(Event(R.string.home_toast_cannot_find_memo))
            }
        }
    }

    fun hideMemoDialog() {
        _memoDialogVisible.postValue(false)
    }

    fun startRecommendation() {
        _openRecommendationEvent.postValue(Event(Unit))
    }

    fun openSelectDialog(selectDialogType: SelectDialogType) {
        _openSelectDialogEvent.postValue(Event(selectDialogType))
        _historyCalendarVisible.value = false
        _moreDialogVisible.value = false
        _selectDialogVisible.value = true
    }

    fun hideAllBackground() {
        _historyCalendarVisible.postValue(false)
        _moreDialogVisible.postValue(false)
        _selectDialogVisible.postValue(false)
        _memoDialogVisible.postValue(false)
    }

    companion object {
        const val TAG = "ReportViewModel"
    }
}