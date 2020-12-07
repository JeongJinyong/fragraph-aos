package com.depromeet.fragraph.feature.recommendation.keyword_select.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.Event
import com.depromeet.fragraph.domain.model.Keyword
import com.depromeet.fragraph.domain.repository.KeywordRepository
import com.depromeet.fragraph.feature.recommendation.keyword_select.model.KeywordNextStatus
import com.depromeet.fragraph.feature.recommendation.keyword_select.model.KeywordUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class KeywordSelectViewModel @ViewModelInject constructor(
    private val keywordRepository: KeywordRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _keywords = MutableLiveData<List<KeywordUiModel>>(listOf())
    val keywords: LiveData<List<KeywordUiModel>>
        get() = _keywords

    private val _selectedKeywordsSize = MutableLiveData(0)
    val selectedKeywordsSize: LiveData<Int>
        get() = _selectedKeywordsSize

    private val _keywordNextStatus = MutableLiveData(KeywordNextStatus.NORMAL)
    val keywordNextStatus: LiveData<KeywordNextStatus>
        get() = _keywordNextStatus

    private val _keywordToastMessageEvent = MutableLiveData<Event<Int>>()
    val keywordToastMessageEvent: LiveData<Event<Int>>
        get() = _keywordToastMessageEvent

    private val _backClickEvent = MutableLiveData<Event<Unit>>()
    val backClickEvent: LiveData<Event<Unit>>
        get() = _backClickEvent

    private val _openIncenseSelectEvent = MutableLiveData<Event<Unit>>()
    val openIncenseSelectEvent: LiveData<Event<Unit>>
        get() = _openIncenseSelectEvent

    init {
        getKeywords()
    }

    private fun getKeywords() {
        viewModelScope.launch(Dispatchers.IO) {
            keywordRepository.getRandomKeywords()
                .map {
                    it.map {keyword ->
                        KeywordUiModel(keyword.id, keyword.name, keyword.weight, keyword.category, MutableLiveData(false))
                    }
                }
                .collect {
                    _keywords.postValue(it)
                }
        }
    }

    fun onRecommendClick() {
        if (_selectedKeywordsSize.value == 3) {
            val selectedKeywords = keywords.value!!
                .filter { it.selected.value!! ?: false }
                .map { Keyword(it.id, it.name, it.weight, it.category) }
            keywordRepository.saveSelectKeywords(selectedKeywords)
            _openIncenseSelectEvent.postValue(Event(Unit))
        } else {
            _keywordNextStatus.postValue(KeywordNextStatus.LESS)
            _keywordToastMessageEvent.postValue(Event(R.string.keyword_toast_less_than_three))
        }
    }

    fun keywordClick(id: Int) {
        val clickedKeyword =  keywords.value!!
            .first { it.id == id }

        if (clickedKeyword.selected.value!!) {
            clickedKeyword.keywordClick()
            _selectedKeywordsSize.value = _selectedKeywordsSize.value!!.minus(1)
            _keywordNextStatus.postValue(KeywordNextStatus.NORMAL)
            return
        }

        if (_selectedKeywordsSize.value == 3) {
            _keywordToastMessageEvent.postValue(Event(R.string.keyword_toast_more_than_three))
        } else {
            keywords.value!!.first { it.id == id }.keywordClick()
            _selectedKeywordsSize.value = _selectedKeywordsSize.value!!.plus(1)
            if (_selectedKeywordsSize.value == 3) {
                _keywordNextStatus.postValue(KeywordNextStatus.OK)
            } else {
                _keywordNextStatus.postValue(KeywordNextStatus.NORMAL)
            }
        }

    }

    fun onBackClick() {
        _backClickEvent.postValue(Event(Unit))
    }

    companion object {
        const val TAG = "KeywordSelectViewModel"
    }
}