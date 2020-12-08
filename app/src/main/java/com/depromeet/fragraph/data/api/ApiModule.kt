package com.depromeet.fragraph.data.api

import android.content.Context
import com.depromeet.fragraph.BuildConfig
import com.depromeet.fragraph.data.api.adapter.response.NetworkResponseAdapterFactory
import com.depromeet.fragraph.data.api.factory.EnumConverterFactory
import com.depromeet.fragraph.data.api.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideFragraphApi(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): FragraphApi =
        Retrofit.Builder()
            .baseUrl(apiEndpoint())
            .client(okHttpClient)
            .addConverterFactory(converter)
            .addConverterFactory(EnumConverterFactory())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(FragraphApi::class.java)

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
//        val gson : Gson = GsonBuilder()
//            .registerTypeAdapter(Date::class.java, DateDeserializer())
//            .serializeNulls()
//            .create()
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(5L, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Singleton
    @Provides
    fun providesAuthInterceptor(
        @ApplicationContext context: Context
    ): AuthInterceptor =
        AuthInterceptor(context)
}