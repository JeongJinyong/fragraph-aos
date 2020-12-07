package com.depromeet.fragraph.data.local

import com.depromeet.fragraph.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun provideLocalData(): LocalData {
        return LocalData()
    }
}