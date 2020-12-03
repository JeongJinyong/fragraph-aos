package com.depromeet.fragraph.feature.meditation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.core.ext.miliSecondsToMinutes
import com.depromeet.fragraph.core.ext.miliSecondsToSeconds
import com.depromeet.fragraph.domain.model.Meditation
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.MeditationRepository
import timber.log.Timber

class MeditationViewModel @ViewModelInject constructor(
    private val meditationRepository: MeditationRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _backEvent = MutableLiveData<Event<Unit>>()
    val backEvent: LiveData<Event<Unit>>
        get() = _backEvent

    private val _meditation = MutableLiveData<Meditation>()
    val meditation: LiveData<Meditation>
        get() = _meditation

    private val _totalPlaytime = MutableLiveData(0)
    val totalPlaytime: LiveData<Int>
        get() = _totalPlaytime

    private val _remainingTime = MutableLiveData(0)
    val remainingTime: LiveData<Int>
        get() = _remainingTime

    private val _remainingTimeMin = MutableLiveData("")
    val remainingTimeMin: LiveData<String>
        get() = _remainingTimeMin

    private val _remainingTimeSeconds = MutableLiveData("")
    val remainingTimeSeconds: LiveData<String>
        get() = _remainingTimeSeconds

    private val _incenseTitle = MutableLiveData(IncenseTitle.EMPTY)
    val incenseTitle: LiveData<IncenseTitle>
        get() = _incenseTitle

    private val _videoUrl = MutableLiveData("")
    val videoUrl: LiveData<String>
        get() = _videoUrl

    private val _musicTitle = MutableLiveData("")
    val musicTitle: LiveData<String>
        get() = _musicTitle

    init {
        meditationRepository.getMeditation()?.let {
            _meditation.value = it
            _videoUrl.postValue(it.video.url)
            _musicTitle.postValue(it.music.title)
            _incenseTitle.postValue(it.incense.title)
        } ?: kotlin.run {
            Timber.d("null???????")
            onErrorMeditation()
        }
    }

    fun initPlayTime(totalTime: Int, remainingTime: Int) {
        Timber.tag(TAG).d("init play time, totalTime: $totalTime, remainingTime: $remainingTime")
        _totalPlaytime.postValue(totalTime)
        this.setRemainingTime(remainingTime)
    }

    fun setRemainingTime(remainingTime: Int) {
        Timber.tag(TAG).d("set remaining time, remainingTime: $remainingTime")
        _remainingTime.postValue(remainingTime)
        _remainingTimeMin.postValue(remainingTime.miliSecondsToMinutes())
        _remainingTimeSeconds.postValue(remainingTime.miliSecondsToSeconds())
    }

    fun onErrorMeditation() {
        _backEvent.postValue(Event(Unit))
    }

    companion object {
        const val TAG = "MeditationViewModel"
    }
}