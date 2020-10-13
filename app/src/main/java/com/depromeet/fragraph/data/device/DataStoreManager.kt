package com.depromeet.fragraph.data.device

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val authPrefKey: Preferences.Key<String>,
) {

    fun getAccessTokenInPref(): Flow<String?> {
        return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[authPrefKey]
            }
    }
}