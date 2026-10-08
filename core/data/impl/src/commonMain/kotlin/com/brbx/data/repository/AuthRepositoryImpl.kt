package com.brbx.data.repository

import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.network.api.AuthApi
import com.brbx.network.model.auth.AuthRequestDto
import com.brbx.network.model.auth.AuthResponseDto
import com.brbx.preferences.AuthPrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

internal class AuthRepositoryImpl(
    private val authPrefs: AuthPrefsManager,
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

    override suspend fun refreshTokens(): RequestResult<Unit> {
        val token = authPrefs.refreshToken.first()
        return if (token == null) {
            failure(exception = RequestException.Unknown)
        } else {
            handler.handle { authApi.refreshTokens(token).toDomain() }.fold(
                onSuccess = { tokens ->
                    authPrefs.saveAccessToken(tokens.access)
                    authPrefs.saveRefreshToken(tokens.refresh)
                    success(Unit)
                },
                onException = { e ->
                    failure(e)
                }
            )
        }
    }

    private fun AuthResponseDto.toDomain() =
        AuthTokens(
            access = accessToken,
            refresh = refreshToken,
        )
}
