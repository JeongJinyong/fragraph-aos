package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getReport(): Flow<Report>
}