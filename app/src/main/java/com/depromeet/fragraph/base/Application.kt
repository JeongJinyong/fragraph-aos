package com.depromeet.fragraph.base

import android.app.Application
import com.depromeet.fragraph.base.initializer.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application: Application() {

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards AppInitializer>

    override fun onCreate() {
        super.onCreate()

        initializers.forEach {
            it.initialize(this)
        }

    }
}