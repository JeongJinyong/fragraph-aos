package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.core.ext.getBodyOrThrow
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.data.api.request.UpdateMyInfoRequest
import com.depromeet.fragraph.data.local.LocalData
import com.depromeet.fragraph.domain.model.User
import com.depromeet.fragraph.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataUserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localData: LocalData,
    private val fragraphApi: FragraphApi,
) : UserRepository {

    override fun getMyInfo(): Flow<User> {
        return flow {
            localData.myInfo?.let { user ->
                emit(user)
            } ?: kotlin.run {
                fragraphApi.getMyInfo().getBodyOrThrow()?.let { userData ->
                    val user = User(
                        userData.data.id,
                        userData.data.nickname,
                        userData.data.profileUrl,
                        userData.data.provider,
                    ).apply {
                        localData.myInfo = this
                    }
                    emit(user)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun updateMyInfo(nickname: String): Flow<User> {
        return flow {
            fragraphApi.updateMyInfo(UpdateMyInfoRequest(nickname)).data?.let {
                val user = User(
                    it.id,
                    it.nickname,
                    it.profileUrl,
                    it.provider,
                ).apply {
                    localData.myInfo = this
                }
                emit(user)
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val TAG = "DataUserRepository"
    }
}