package com.depromeet.fragraph.feature.recommendation.feeling_select.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.repository.UserRepository
import com.depromeet.fragraph.feature.recommendation.feeling_select.model.FeelingUiModel
import timber.log.Timber

class FeelingSelectViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _feelings = MutableLiveData<List<FeelingUiModel>>(listOf())
    val feelings: LiveData<List<FeelingUiModel>>
        get() = _feelings

    private val _openIncenseSelectEvent = MutableLiveData<Event<Unit>>()
    val openIncenseSelectEvent: LiveData<Event<Unit>>
        get() = _openIncenseSelectEvent

    init {
        _feelings.value = listOf(
            FeelingUiModel(1, "잠 못드는 밤", 0.3f, MutableLiveData(false)),
            FeelingUiModel(2, "우울해", 0.4f, MutableLiveData(false)),
            FeelingUiModel(3, "잠이 안와", 0.8f, MutableLiveData(false)),
            FeelingUiModel(4, "힘든 하루", 0.2f, MutableLiveData(false)),
            FeelingUiModel(5, "스트레스", 0.5f, MutableLiveData(false)),
            FeelingUiModel(6, "마음대로 되지 않는 하루", 0.3f, MutableLiveData(false)),
            FeelingUiModel(7, "멀리 떠나고 싶은", 0.4f, MutableLiveData(false)),
            FeelingUiModel(8, "토닥토닥", 0.1f, MutableLiveData(false)),
            FeelingUiModel(9, "머리가 복잡해", 0.6f, MutableLiveData(false)),
            FeelingUiModel(10, "집중이 필요한", 0.8f, MutableLiveData(false)),
            FeelingUiModel(11, "상쾌해", 0.2f, MutableLiveData(false)),
            FeelingUiModel(12, "불안불안", 0.3f, MutableLiveData(false)),
            FeelingUiModel(13, "망치면 어떡하지", 0.2f, MutableLiveData(false)),
            FeelingUiModel(14, "상쾌해 지고싶은", 0.5f, MutableLiveData(false)),
            FeelingUiModel(15, "민초단", 0.6f, MutableLiveData(false)),
            FeelingUiModel(16, "주변이 왜 이렇게 시끄럽지?", 0.1f, MutableLiveData(false)),
            FeelingUiModel(17, "아직 할 일이 남은", 0.4f, MutableLiveData(false)),
            FeelingUiModel(18, "공부할 타이밍", 0.6f, MutableLiveData(false)),
            FeelingUiModel(19, "우울해", 0.7f, MutableLiveData(false)),
            FeelingUiModel(20, "사랑하고 싶어", 0.6f, MutableLiveData(false)),
            FeelingUiModel(21, "머리가 지끈거리는", 0.5f, MutableLiveData(false)),
            FeelingUiModel(22, "목이 칼칼해", 0.4f, MutableLiveData(false)),
            FeelingUiModel(23, "혈압 상승!!", 0.3f, MutableLiveData(false)),
            FeelingUiModel(24, "분노조절장애", 0.5f, MutableLiveData(false)),
            FeelingUiModel(25, "머리가 띵!", 0.6f, MutableLiveData(false)),
            FeelingUiModel(26, "고단한", 0.7f, MutableLiveData(false)),
            FeelingUiModel(27, "허탈한", 0.8f, MutableLiveData(false)),
            FeelingUiModel(28, "으슬으슬", 0.2f, MutableLiveData(false)),
            FeelingUiModel(29, "솟아라 엔돌핀이여!", 0.3f, MutableLiveData(false)),
            FeelingUiModel(30, "정신적 스트레스", 0.4f, MutableLiveData(false)),
            FeelingUiModel(31, "활기찬 하루", 0.6f, MutableLiveData(false)),
            FeelingUiModel(32, "신나는", 0.7f, MutableLiveData(false)),
            FeelingUiModel(33, "기운없는", 0.8f, MutableLiveData(false)),
            FeelingUiModel(34, "지친 하루 끝에서", 0.8f, MutableLiveData(false)),
            FeelingUiModel(35, "시체가 되고 싶어", 0.3f, MutableLiveData(false)),
            FeelingUiModel(36, "아무것도 하기 싫어", 0.8f, MutableLiveData(false)),
            FeelingUiModel(37, "번아웃 증후군", 0.5f, MutableLiveData(false)),
            FeelingUiModel(38, "기분전환이 필요해", 0.7f, MutableLiveData(false)),
            FeelingUiModel(39, "하늘을 날아갈 것 같은", 0.3f, MutableLiveData(false)),
            FeelingUiModel(40, "설레는", 0.1f, MutableLiveData(false)),
            FeelingUiModel(41, "웃음이 나오는", 0.7f, MutableLiveData(false)),
            FeelingUiModel(42, "색다른 것을 하고 싶은", 0.6f, MutableLiveData(false)),
            FeelingUiModel(43, "안정이 필요한", 0.8f, MutableLiveData(false)),
            FeelingUiModel(44, "꿀꿀한", 0.3f, MutableLiveData(false)),
            FeelingUiModel(45, "하루의 마무리", 0.5f, MutableLiveData(false)),
            FeelingUiModel(46, "마음이 뒤숭숭해", 0.1f, MutableLiveData(false)),
            FeelingUiModel(47, "생각이 생각을 불러오는", 0.4f, MutableLiveData(false)),
            FeelingUiModel(48, "너무 신이나", 0.3f, MutableLiveData(false)),
            FeelingUiModel(49, "명상이 필요한", 0.2f, MutableLiveData(false)),
        )
    }

//    fun onFeelingClick() {
//        val newFeelings = feelings.value!!
//            .map {
//                if (it.id == feelingId) {
//                    FeelingUiModel(
//                        it.id,
//                        it.name,
//                        it.weight,
//                        !it.selected,
//                    )
//                }
//                it
//            }
//        _feelings.postValue(newFeelings)
//    }

    fun onRecommendClick() {
        val feelingIds = feelings.value!!
            .filter { it.selected.value ?: false }
            .map {
                Timber.tag(TAG).d("selected feeling: ${it.name}")
                it.id
            }

        Timber.tag(TAG).d("tagids : $feelingIds")
        _openIncenseSelectEvent.postValue(Event(Unit))
    }

    companion object {
        const val TAG = "FeelingSelectViewModel"
    }
}