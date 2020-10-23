package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.data.api.request.UpdateMyInfoRequest
import com.depromeet.fragraph.domain.model.User
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
) : UserRepository {

    var myInfo: User? = null

    override fun getMyInfo(): Flow<User> {
        return flow {
            myInfo?.let { user ->
                emit(user)
            } ?: kotlin.run {
                fragraphApi.getMyInfo().data?.let { userData ->
                    val user = User(
                        userData.id,
                        userData.nickname,
                        userData.profileUrl,
                        userData.provider,
                    ).apply {
                        myInfo = this
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
                    myInfo = this
                }
                emit(user)
            }
        }.flowOn(Dispatchers.IO)
    }
}