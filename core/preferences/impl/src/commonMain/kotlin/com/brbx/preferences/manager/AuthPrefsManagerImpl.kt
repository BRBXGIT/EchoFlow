package com.brbx.preferences.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.Flow

internal class AuthPrefsManagerImpl(
    store: DataStore<Preferences>,
) : BasePrefsManager(store), AuthPrefsManager {

    override val accessToken: Flow<String?> = getValue(accessTokenKey)
    override val refreshToken: Flow<String?> = getValue(refreshTokenKey)

    override suspend fun saveAccessToken(token: String) = setValue(accessTokenKey, token)

    override suspend fun saveRefreshToken(token: String) = setValue(refreshTokenKey, token)

    override suspend fun clearAccessToken() = clearValue(accessTokenKey)

    override suspend fun clearRefreshToken() = clearValue(refreshTokenKey)

    private companion object {
        private const val accessTokenKeyName = "access_token"
        private const val refreshTokenKeyName = "refresh_token"

        val accessTokenKey = stringPreferencesKey(name = accessTokenKeyName)
        val refreshTokenKey = stringPreferencesKey(name = refreshTokenKeyName)
    }
}