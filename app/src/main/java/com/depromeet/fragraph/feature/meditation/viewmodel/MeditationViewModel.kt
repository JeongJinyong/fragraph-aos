package com.depromeet.fragraph.feature.meditation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.core.ext.miliSecondsToMinutes
import com.depromeet.fragraph.core.ext.miliSecondsToSeconds
import timber.log.Timber

class MeditationViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _day = MutableLiveData(0)
    val day: LiveData<Int>
        get() = _day

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

    private val _incense = MutableLiveData("라벤더")
    val incense: LiveData<String>
        get() = _incense

    private val _videoUrl = MutableLiveData("https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_1.gif")
    val videoUrl: LiveData<String>
        get() = _videoUrl

    private val _musicTitle = MutableLiveData("https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_1.mp3")
    val musicTitle: LiveData<String>
        get() = _musicTitle

    private val _musicUrl = MutableLiveData("https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_1.mp3")
    val musicUrl: LiveData<String>
        get() = _musicUrl

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

    companion object {
        const val TAG = "MeditationViewModel"
    }
}