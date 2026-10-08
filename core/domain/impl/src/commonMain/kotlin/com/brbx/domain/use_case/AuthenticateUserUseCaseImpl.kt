package com.brbx.domain.use_case

import com.brbx.data.builder.AuthUrlBuilder
import com.brbx.data.handler.AuthCallbackHandler
import com.brbx.data.repository.AuthRepository
import com.brbx.domain.model.common.RequestResult
import com.brbx.domain.model.common.failure
import com.brbx.domain.model.enums.RequestException

internal class AuthenticateUserUseCaseImpl(
    private val authCallbackHandler: AuthCallbackHandler,
    private val authUrlBuilder: AuthUrlBuilder,
    private val authRepository: AuthRepository,
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
                .also { authUrlBuilder.clear() }
        } ?: failure(exception = RequestException.Unknown).also { authUrlBuilder.clear() }
    }
}
