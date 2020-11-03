package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.data.api.request.UpdateMyInfoRequest
import com.depromeet.fragraph.domain.model.Incense
import com.depromeet.fragraph.domain.model.User
import com.depromeet.fragraph.domain.repository.IncenseRepository
import com.depromeet.fragraph.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataFragraphRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fragraphApi: FragraphApi,
) : IncenseRepository {

    override fun getIncenses(): Flow<List<Incense>> {
        return flow {
            fragraphApi.getIncenses().data?.let {incenseData ->
                val incenses = incenseData.incenses
                    .map { Incense(it.id, it.title, it.detail, "") }
                emit(incenses)
            }
        }
    }
}