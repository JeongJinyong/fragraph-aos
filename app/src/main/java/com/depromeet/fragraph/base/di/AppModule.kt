package com.depromeet.fragraph.base.di

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.depromeet.fragraph.base.initializer.AppInitializer
import com.depromeet.fragraph.base.initializer.KakaoInitializer
import com.depromeet.fragraph.base.initializer.TimberInitializer
import com.depromeet.fragraph.core.player.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.multibindings.IntoSet
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun providePlayer(): Player {
        return Player()
    }

    @Singleton
    @Provides
    fun provideInputManager(
        @ApplicationContext context: Context
    ): InputMethodManager {
        return getSystemService(context, InputMethodManager::class.java) as InputMethodManager
    }
}