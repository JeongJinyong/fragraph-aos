package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface KeywordRepository {

    fun getRandomKeywords(): Flow<List<Keyword>>
    fun saveSelectKeywords(keywords: List<Keyword>)
}