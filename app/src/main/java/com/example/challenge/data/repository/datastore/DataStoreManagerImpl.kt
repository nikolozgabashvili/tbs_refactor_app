package com.example.challenge.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.challenge.domain.repository.datastore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {

    override suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        withContext(Dispatchers.IO) {

            dataStore.edit { settings ->
                settings[key] = value
            }
        }
    }

    override fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it.clear()
            }
        }
    }
}