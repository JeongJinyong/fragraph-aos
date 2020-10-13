package com.depromeet.fragraph.data.repository

import com.depromeet.fragraph.data.device.DataStoreManager
import com.depromeet.fragraph.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataAuthRepository @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : AuthRepository {

    override fun getAccessToken(): Flow<String?> {
        return dataStoreManager.getAccessTokenInPref()
    }
}