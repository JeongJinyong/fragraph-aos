package com.depromeet.fragraph.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): Flow<String?>
}