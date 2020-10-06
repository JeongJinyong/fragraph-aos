package com.depromeet.fragraph.base.initializer

import android.app.Application
import android.util.Log
import com.depromeet.fragraph.BuildConfig
import timber.log.Timber

class TimberInitializer: AppInitializer {
    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        else {
            // Todo:: crash 쪽으로 init !!
        }
    }
}
