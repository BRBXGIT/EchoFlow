package com.brbx.data.repository

import com.brbx.domain.model.UserAuthState
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class UserAuthRepositoryImpl(
    authPrefs: AuthPrefsManager,
) : UserAuthRepository {
    override val userAuthState: Flow<UserAuthState> =
        combine(
            flow = authPrefs.accessToken,
            flow2 = authPrefs.refreshToken,
        ) { accessToken, refreshToken ->
            if (accessToken == null && refreshToken == null) {
                UserAuthState.Unauthorized
            } else {
                UserAuthState.Authorized
            }
        }
}