package com.prestamolab.ctma.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "prestamolab_preferences")

class UserPreferences(private val context: Context) {
    private val availableOnlyKey = booleanPreferencesKey("available_only")
    private val lastSyncKey = longPreferencesKey("last_sync")

    val availableOnly: Flow<Boolean> = context.dataStore.data.map { it[availableOnlyKey] ?: false }
    val lastSync: Flow<Long> = context.dataStore.data.map { it[lastSyncKey] ?: 0L }

    suspend fun setAvailableOnly(value: Boolean) {
        context.dataStore.edit { it[availableOnlyKey] = value }
    }

    suspend fun markSynced() {
        context.dataStore.edit { it[lastSyncKey] = System.currentTimeMillis() }
    }
}
