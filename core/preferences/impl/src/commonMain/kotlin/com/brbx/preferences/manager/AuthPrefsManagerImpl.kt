package com.brbx.preferences.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AuthPrefsManagerImpl(
    private val store: DataStore<Preferences>,
) : BasePrefsManager(store), AuthPrefsManager {

    override val tokens = store.data.map { preferences ->
        val access = preferences[accessTokenKey]
        val refresh = preferences[refreshTokenKey]
        if (access != null && refresh != null) {
            access to refresh
        } else null
    }

    override suspend fun saveTokens(access: String, refresh: String) {
        store.edit { preferences ->
            preferences[accessTokenKey] = access
            preferences[refreshTokenKey] = refresh
        }
    }

    override suspend fun clearTokens() {
        store.edit { preferences ->
            preferences.remove(accessTokenKey)
            preferences.remove(refreshTokenKey)
        }
    }

    private companion object {
        private const val accessTokenKeyName = "access_token"
        private const val refreshTokenKeyName = "refresh_token"

        val accessTokenKey = stringPreferencesKey(name = accessTokenKeyName)
        val refreshTokenKey = stringPreferencesKey(name = refreshTokenKeyName)
    }
}
