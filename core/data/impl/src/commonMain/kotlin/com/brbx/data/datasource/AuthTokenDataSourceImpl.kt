package com.brbx.data.datasource

import com.brbx.domain.model.auth.AuthTokens
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.combine

internal class AuthTokenDataSourceImpl(
    private val authPrefsManager: AuthPrefsManager,
) : AuthTokenDataSource {
    override val tokens = combine(
        flow = authPrefsManager.accessToken,
        flow2 = authPrefsManager.refreshToken,
    ) { access, refresh ->
        if (access != null && refresh != null) AuthTokens(access, refresh) else null
    }

    override suspend fun saveTokens(tokens: AuthTokens) {
        authPrefsManager.saveAccessToken(tokens.access)
        authPrefsManager.saveRefreshToken(tokens.refresh)
    }

    override suspend fun clearTokens() {
        authPrefsManager.clearAccessToken()
        authPrefsManager.clearRefreshToken()
    }
}
