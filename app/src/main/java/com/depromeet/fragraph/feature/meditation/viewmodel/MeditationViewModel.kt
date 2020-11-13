package com.depromeet.fragraph.feature.meditation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MeditationViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _day = MutableLiveData(0)
    val day: LiveData<Int>
        get() = _day

    private val _totalPlaytime = MutableLiveData(300)
    val totalPlaytime: LiveData<Int>
        get() = _totalPlaytime

    private val _remainingTime = MutableLiveData(20)
    val remainingTime: LiveData<Int>
        get() = _remainingTime

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
}