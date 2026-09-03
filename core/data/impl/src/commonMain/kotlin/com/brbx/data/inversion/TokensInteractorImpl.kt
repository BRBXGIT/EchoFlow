package com.brbx.data.inversion

import com.brbx.data.interactor.UserAuthInteractor
import com.brbx.domain.model.AuthTokens
import com.brbx.network.inversion.TokensInteractor
import com.brbx.network.model.Tokens
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class TokensInteractorImpl(
    private val interactor: UserAuthInteractor,
) : TokensInteractor {
    override suspend fun getTokens(): Tokens =
        interactor.tokens.filterNotNull().first().toApi()

    override suspend fun setTokens(tokens: Tokens) =
        interactor.saveTokens(tokens.toDomain())

    override suspend fun clearTokens() =
        interactor.clearTokens()

    private fun AuthTokens.toApi(): Tokens =
        Tokens(this.access, this.refresh)

    private fun Tokens.toDomain(): AuthTokens =
        AuthTokens(this.access, this.refresh)
}