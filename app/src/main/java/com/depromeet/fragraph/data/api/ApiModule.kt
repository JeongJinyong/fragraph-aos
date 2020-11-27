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

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    fun provideGiftApi(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): FragraphApi =
        Retrofit.Builder()
            .baseUrl(apiEndpoint())
            .client(okHttpClient)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(converter)
            .addConverterFactory(EnumConverterFactory())
            .build()
            .create(FragraphApi::class.java)

    @Provides
    fun provideConverterFactory(): Converter.Factory {
//        val gson : Gson = GsonBuilder()
//            .registerTypeAdapter(Date::class.java, DateDeserializer())
//            .serializeNulls()
//            .create()
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    fun providesAuthInterceptor(
        @ApplicationContext context: Context
    ): AuthInterceptor =
        AuthInterceptor(context)
}