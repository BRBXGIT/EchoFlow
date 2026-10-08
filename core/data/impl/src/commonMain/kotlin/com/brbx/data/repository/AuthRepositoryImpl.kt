package com.brbx.data.repository

import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.domain.model.auth.AuthTokens
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.common.fold
import com.brbx.domain.model.common.success
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.network.api.AuthApi
import com.brbx.network.model.auth.AuthRequestDto
import com.brbx.network.model.auth.AuthResponseDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class AuthRepositoryImpl(
    private val authTokenDataSource: AuthTokenDataSource,
    private val authApi: AuthApi,
    private val handler: NetworkResponseHandler,
) : AuthRepository {

    override val userAuthState = authTokenDataSource.tokens.map { tokens ->
        if (tokens == null) {
            UserAuthState.Unauthorized
        } else {
            UserAuthState.Authorized
        }
    }

    override suspend fun authenticate(code: String, verifier: String): RequestResult<Unit> {
        val dto = AuthRequestDto(
            code = code,
            codeVerifier = verifier,
        )
        return handler.handle { authApi.exchangeCode(authRequest = dto).toDomain() }
            .fold(
                onException = ::failure,
                onSuccess = { tokens ->
                    authTokenDataSource.saveTokens(AuthTokens(tokens.access, tokens.refresh))
                    success(Unit)
                },
            )
    }

    override suspend fun refreshTokens(): RequestResult<Unit> {
        val tokens = authTokenDataSource.tokens.first()
        return if (tokens == null) {
            failure(exception = RequestException.Unknown)
        } else {
            handler.handle { authApi.refreshTokens(tokens.refresh).toDomain() }.fold(
                onSuccess = { newTokens ->
                    authTokenDataSource.saveTokens(newTokens)
                    success(Unit)
                },
                onException = ::failure,
            )
        }
    }

    private fun AuthResponseDto.toDomain() =
        AuthTokens(
            access = accessToken,
            refresh = refreshToken,
        )
}
