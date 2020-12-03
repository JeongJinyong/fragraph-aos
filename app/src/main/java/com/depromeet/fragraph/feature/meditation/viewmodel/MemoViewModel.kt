package com.depromeet.fragraph.feature.meditation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.depromeet.fragraph.domain.repository.HistoryRepository

class MemoViewModel @ViewModelInject constructor(
    private val historyRepository: HistoryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}