package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getMyInfo(): Flow<User>
    fun updateMyInfo(nickname: String): Flow<User>
}