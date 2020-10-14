package com.depromeet.fragraph.data.api.interceptor

import android.content.Context
import com.depromeet.fragraph.core.KEY_AUTH_TOKEN
import com.depromeet.fragraph.core.ext.authSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {

        val token = context.authSharedPreferences().getString(KEY_AUTH_TOKEN, null)

        token?.let {
            val newRequest = request().newBuilder().run {
                addHeader("Authorization", "Bearer $token")
                build()
            }
            proceed(newRequest)
        } ?: run {
            proceed(request())
        }
    }
}