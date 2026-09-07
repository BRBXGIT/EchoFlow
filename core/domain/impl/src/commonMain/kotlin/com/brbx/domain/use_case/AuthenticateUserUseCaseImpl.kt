package com.brbx.domain.use_case

import com.brbx.data.builder.AuthUrlBuilder
import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.handler.AuthCallbackHandler
import com.brbx.data.repository.AuthRepository
import com.brbx.domain.model.AuthTokens
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.util.RequestResult
import com.brbx.domain.model.util.failure
import com.brbx.domain.model.util.map
import com.brbx.domain.model.util.onSuccess

internal class AuthenticateUserUseCaseImpl(
    private val authCallbackHandler: AuthCallbackHandler,
    private val authUrlBuilder: AuthUrlBuilder,
    private val authRepository: AuthRepository,
    private val authTokenDataSource: AuthTokenDataSource,
) : AuthenticateUserUseCase {
    override suspend fun invoke(rawUri: String): RequestResult<Unit> {
        val deeplinkPayload = authCallbackHandler.handle(rawUri)
            ?: return failure(exception = RequestException.Unknown)

        val savedState = authUrlBuilder.currentState
        val savedVerifier = authUrlBuilder.currentCodeVerifier

        if (deeplinkPayload.state != savedState) {
            authUrlBuilder.clear()
            return failure(exception = RequestException.CsrfAttack)
        }

        return savedVerifier?.let { verifier ->
            authRepository.authenticate(deeplinkPayload.code, verifier)
                .onSuccess { codes ->
                    authTokenDataSource.saveTokens(AuthTokens(codes.access, codes.refresh))
                }
                .map {}
        } ?: failure(exception = RequestException.Unknown)
    }
}
