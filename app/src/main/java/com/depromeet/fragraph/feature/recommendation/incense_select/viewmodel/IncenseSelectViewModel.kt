package com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.model.*
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.HistoryRepository
import com.depromeet.fragraph.domain.repository.IncenseRepository
import com.depromeet.fragraph.domain.repository.MeditationRepository
import com.depromeet.fragraph.feature.recommendation.incense_select.model.IncenseItemUiModel
import com.depromeet.fragraph.feature.signin.SignInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class IncenseSelectViewModel @ViewModelInject constructor(
    private val incenseRepository: IncenseRepository,
    private val meditationRepository: MeditationRepository,
    private val historyRepository: HistoryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    lateinit var selectedIncenseUiModel: IncenseItemUiModel

    private val _incenseSelectToastMessageEvent = MutableLiveData<Event<Int>>()
    val incenseSelectToastMessageEvent: LiveData<Event<Int>>
        get() = _incenseSelectToastMessageEvent

    private val _backClickEvent = MutableLiveData<Event<Unit>>()
    val backClickEvent: LiveData<Event<Unit>>
        get() = _backClickEvent

    private val _openMeditationEvent = MutableLiveData<Event<Unit>>()
    val openMeditationEvent: LiveData<Event<Unit>>
        get() = _openMeditationEvent

    private val _playtimePickerVisible = MutableLiveData(false)
    val playtimePickerVisible: LiveData<Boolean>
        get() = _playtimePickerVisible

    private val _playtime = MutableLiveData(0)
    val playtime: LiveData<Int>
        get() = _playtime

    private val _incenses = MutableLiveData<List<IncenseItemUiModel>>(listOf())
    val incenses: LiveData<List<IncenseItemUiModel>>
        get() = _incenses

    init {
        initRecommendationIncenses()
    }

    private fun initRecommendationIncenses() {
        viewModelScope.launch(Dispatchers.IO) {
            incenseRepository.getRecommendationIncenses()
                .catch {
                    Timber.d("recommendation 가져오는 중 에러")
                }
                .map {recList ->
                    recList.map {
                        IncenseItemUiModel(
                            it.incense.id, it.incense.title, it.incense.content, it.incense.image, it.incense.category,
                            it.video, it.music, it.keyword,
                            MutableLiveData(false), MutableLiveData(true)
                        )
                    }
                }
                .collect {
                    Timber.d("rec size: ${it.size}")
                    _incenses.postValue(it)
                    selectedIncenseUiModel = it[0]
                }
        }
    }

    fun setPlaytime(playtime: Int) {
        _playtime.value = playtime
        _playtimePickerVisible.postValue(false)
    }

    fun onPlaytimeClick() {
        _playtimePickerVisible.postValue(true)
    }

    fun onPlaytimeBackgroundClick() {
        _playtimePickerVisible.postValue(false)
    }

    fun changeSelectedIncense(position: Int) {
        selectedIncenseUiModel = incenses.value!![position]
    }

    fun onMeditationStart() {
        if (playtime.value!! <= 0) {
            _incenseSelectToastMessageEvent.postValue(Event(R.string.incense_select_playtime_less_than_0))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                historyRepository.saveHistories(
                    selectedIncenseUiModel.keywords,
                    selectedIncenseUiModel.toIncense(),
                    playtime.value!!
                ).catch {
                    Timber.tag(TAG).e("명상 시작 시 에러 발생 !!!!")
                }.map { id ->
                    Meditation(
                        id, playtime.value!!,
                        System.currentTimeMillis(),
                        selectedIncenseUiModel.toIncense(),
                        selectedIncenseUiModel.music,
                        selectedIncenseUiModel.video
                    )
                }.collect {
                    meditationRepository.setMeditation(it)
                    _openMeditationEvent.postValue(Event(Unit))
                }
            }
        }
    }

    fun onBackClick() {
        _backClickEvent.postValue(Event(Unit))
    }

    companion object {
        const val TAG = "IncenseSelectViewModel"
    }
}