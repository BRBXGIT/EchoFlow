package com.brbx.domain.use_case

import com.brbx.data.builder.AuthLinkBuilder
import com.brbx.data.handler.AuthDeeplinkHandler
import com.brbx.data.interactor.UserAuthInteractor
import com.brbx.data.repository.UserAuthRepository
import com.brbx.domain.model.AuthTokens
import com.brbx.domain.model.RequestResult
import com.brbx.domain.model.enums.RequestException
import com.brbx.domain.model.failure
import com.brbx.domain.model.map
import com.brbx.domain.model.onSuccess

internal class AuthenticateUserUseCaseImpl(
    private val handler: AuthDeeplinkHandler,
    private val authLinkBuilder: AuthLinkBuilder,
    private val authRepository: UserAuthRepository,
    private val authInteractor: UserAuthInteractor,
) : AuthenticateUserUseCase {
    override suspend fun invoke(rawUri: String): RequestResult<Unit> {
        val deeplinkPayload = handler.handle(rawUri)
            ?: return failure(exception = RequestException.Unknown)

        val savedState = authLinkBuilder.currentState
        val savedVerifier = authLinkBuilder.currentCodeVerifier

        if (deeplinkPayload.state != savedState) {
            authLinkBuilder.clear()
            return failure(exception = RequestException.CsrfAttack)
        }

        return savedVerifier?.let { verifier ->
            authRepository.authenticate(deeplinkPayload.code, verifier)
                .onSuccess { codes ->
                    authInteractor.saveTokens(AuthTokens(codes.access, codes.refresh))
                }
                .map {}
        } ?: failure(exception = RequestException.Unknown)
    }
}