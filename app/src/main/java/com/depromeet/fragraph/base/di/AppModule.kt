package com.depromeet.fragraph.base.di

import android.app.Application
import android.content.Context
import com.depromeet.fragraph.base.initializer.AppInitializer
import com.depromeet.fragraph.base.initializer.KakaoInitializer
import com.depromeet.fragraph.base.initializer.TimberInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @IntoSet
    fun provideTimberInitializer(): AppInitializer {
        return TimberInitializer()
    }

    @Provides
    @IntoSet
    fun provideKakaoInitializer(): AppInitializer {
        return KakaoInitializer()
    }
}