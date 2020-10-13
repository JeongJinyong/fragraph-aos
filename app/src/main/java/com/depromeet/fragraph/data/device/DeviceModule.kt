package com.depromeet.fragraph.data.device

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DeviceModule {

    @Provides
    fun provideDefaultDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.createDataStore("DEFAULT_DATA_STORE")
    }

    @Provides
    fun provideAccessPrefKey(): Preferences.Key<String> {
        return preferencesKey("KEY_ACCESS_TOKEN")
    }

    @Provides
    fun providesDataStoreManager(
        dataStore :DataStore<Preferences>,
        authPrefKey: Preferences.Key<String>,
    ): DataStoreManager {
        return DataStoreManager(dataStore, authPrefKey)
    }
}