package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.core.KEY_AUTH_TOKEN
import com.depromeet.fragraph.core.ext.authSharedPreferences
import com.depromeet.fragraph.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataAuthRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthRepository {

    override fun getAccessToken(): String? {
        return context.authSharedPreferences().getString(KEY_AUTH_TOKEN, null)
    }

//    override fun loginWithKakao(kakaoToken: String): Flow<String> {
//
//    }
}