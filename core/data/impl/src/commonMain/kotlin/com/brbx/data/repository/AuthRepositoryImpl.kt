package com.brbx.data.repository

import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.AuthTokens
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.domain.model.util.RequestResult
import com.brbx.network.api.AuthApi
import com.brbx.network.model.AuthRequestDto
import com.brbx.network.model.AuthResponseDto
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class AuthRepositoryImpl(
    authPrefs: AuthPrefsManager,
    private val authApi: AuthApi,
    private val handler: NetworkResponseHandler,
) : AuthRepository {
    override val userAuthState: Flow<UserAuthState> =
        combine(
            flow = authPrefs.accessToken,
            flow2 = authPrefs.refreshToken,
        ) { accessToken, refreshToken ->
            if (accessToken == null && refreshToken == null) {
                UserAuthState.Unauthorized
            } else UserAuthState.Authorized
        }

    override suspend fun authenticate(code: String, verifier: String): RequestResult<AuthTokens> {
        val dto = AuthRequestDto(
            code = code,
            codeVerifier = verifier
        )
        return handler.handle { authApi.exchangeCode(authRequest = dto).toDomain() }
    }

    private fun AuthResponseDto.toDomain() =
        AuthTokens(
            access = accessToken,
            refresh = refreshToken,
        )
}
