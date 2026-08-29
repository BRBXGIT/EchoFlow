package com.brbx.data.interactor

import com.brbx.domain.model.AuthTokens
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.combine

internal class UserAuthInteractorImpl(
    private val authPrefsManager: AuthPrefsManager,
) : UserAuthInteractor {
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
}