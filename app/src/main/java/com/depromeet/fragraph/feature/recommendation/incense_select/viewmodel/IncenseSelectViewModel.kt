package com.depromeet.fragraph.feature.recommendation.incense_select.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.model.Category
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.model.Music
import com.depromeet.fragraph.domain.model.Video
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.feature.recommendation.incense_select.model.IncenseItemUiModel
import timber.log.Timber

class IncenseSelectViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

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
        val categories = listOf(
            Category(1, "숙면"),
            Category(2, "집중"),
            Category(3, "스트레스"),
            Category(4, "기분전환"),
            Category(5, "명상"),
        )

        val music = Music(
            1,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/music/music_1.mp3"
        )

        val video = Video(
            1,
            "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/video/video_1.gif"
        )

        val feelings = listOf(
            Keyword(2, "우울해", 0.4f),
            Keyword(1, "하늘을 날아갈 것 같은", 0.8f),
            Keyword(3, "잠 못드는 밤", 0.3f),
        )

        _incenses.value = listOf(
            IncenseItemUiModel(3, IncenseTitle.LAVENDER, "주성분은 아세트산리날릴, 리날올, 피넨, 리모넨, 게라니올, 시네올 등이다. 이는 신경을 안정시켜주고 스트레스 해소 및 불면증 예방에 탁월한 효과가 있다.", "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/lavendar.png", categories[0], video, music, feelings, MutableLiveData(false), MutableLiveData(true)),
            IncenseItemUiModel(4, IncenseTitle.PEPPERMINT, "기침, 감기, 천식, 알레르기 및 결핵 등 호흡기계에 건강상 효능을 제공, 기억력 증진 및 스트레스 완화 효과가 있을 수 있습니다.", "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/mint.png", categories[1], video, music, feelings, MutableLiveData(false), MutableLiveData(false)),
            IncenseItemUiModel(5, IncenseTitle.EUCALYPTUS, "근육통 안화효과, 다피가료움증 개선, 호흡기 건강에 도움을 줍니다.", "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/Eucalyptus.png", categories[2], video, music, feelings, MutableLiveData(false), MutableLiveData(false)),
            IncenseItemUiModel(6, IncenseTitle.ORANGE, "림프 흐름을 자극하여 부은 조직의 치료를 돕고, 셀룰라이트 처치에도 사용됩니다. 건성 피부, 염증이 있는 피부, 여드름 성향의 피부를 진정시키는 데 유용합니다. 또한 재생성이 있어 노화 피부와 거칠고 굳어진 피부치료에 사용하면 좋습니다.", "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/orange.png", categories[3], video, music, feelings, MutableLiveData(false), MutableLiveData(false)),
            IncenseItemUiModel(7, IncenseTitle.SANDALWOOD, "심장기능을 강화하고 혈액순환을 촉진, 마음을 진정시키는 효과가 뛰어납니다.", "https://fragraph-contents.s3.ap-northeast-2.amazonaws.com/incense_image/sandalwood.png", categories[4], video, music, feelings, MutableLiveData(false), MutableLiveData(false)),
        )
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

    fun onMeditationStart() {
        _openMeditationEvent.postValue(Event(Unit))
    }

    fun onBackClick() {
        _backClickEvent.postValue(Event(Unit))
    }

    companion object {
        const val TAG = "IncenseSelectViewModel"
    }
}