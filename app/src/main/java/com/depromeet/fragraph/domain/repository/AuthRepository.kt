package com.depromeet.fragraph.domain.repository

import com.depromeet.fragraph.domain.type.UserCreated
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): Flow<String?>
    fun loginWithKakao(kakaoToken: String): Flow<UserCreated>
}