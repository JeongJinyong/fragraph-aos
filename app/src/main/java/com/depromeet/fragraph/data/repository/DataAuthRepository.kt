package com.depromeet.fragraph.data.repository

import android.content.Context
import com.depromeet.fragraph.core.KEY_AUTH_TOKEN
import com.depromeet.fragraph.core.ext.authSharedPreferences
import com.depromeet.fragraph.data.api.FragraphApi
import com.depromeet.fragraph.data.api.request.SocialLoginRequest
import com.depromeet.fragraph.domain.repository.AuthRepository
import com.depromeet.fragraph.domain.type.UserCreated
import com.depromeet.fragraph.feature.signin.viewmodel.SignInViewModel
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class DataAuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fragraphApi: FragraphApi,
) : AuthRepository {

    override fun getAccessToken(): Flow<String?> {
        return flow {
            emit(context.authSharedPreferences().getString(KEY_AUTH_TOKEN, null))
        }
    }

    override fun loginWithKakao(kakaoToken: String): Flow<UserCreated> {
        return flow {
            fragraphApi.loginWithSocial("kakao", SocialLoginRequest(kakaoToken)).data?.let {
                context
                    .authSharedPreferences()
                    .edit()
                    .putString(KEY_AUTH_TOKEN, it.jwt)
                    .commit()

                emit(it.userCreated)
            }
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    private fun getTokenByKakao() {

    }
}