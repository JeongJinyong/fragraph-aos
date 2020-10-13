package com.depromeet.fragraph.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): String?
//    fun loginWithKakao(kakaoToken: String): Flow<String>
}