package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.Report
import com.depromeet.fragraph.domain.model.enums.IncenseTitle
import com.depromeet.fragraph.domain.repository.IncenseRepository
import com.depromeet.fragraph.domain.repository.ReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataFragraphRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fragraphApi: FragraphApi,
) : IncenseRepository, ReportRepository {

    override fun getIncenses(): Flow<List<Incense>> {
        return flow {
            fragraphApi.getIncenses().data?.let {incenseData ->
                val incenses = incenseData.incenses
                    .map { Incense(it.id, it.title, it.detail, "") }
                emit(incenses)
            }
        }
    }

    override fun getReport(): Flow<Report> {
        return flow {
            val titles = listOf(IncenseTitle.LAVENDER, IncenseTitle.PEPPERMINT, IncenseTitle.SANDALWOOD, IncenseTitle.ORANGE, IncenseTitle.EUCALYPTUS)
            val values = listOf(7f, 4f, 8f, 5f, 10f)
            emit(Report(titles, values))
        }
    }
}