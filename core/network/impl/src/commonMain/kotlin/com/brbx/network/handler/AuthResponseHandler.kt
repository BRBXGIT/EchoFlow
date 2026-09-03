package com.brbx.network.handler

import com.brbx.network.inversion.TokensInteractor
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

internal interface AuthResponseHandler {
    suspend fun <T> handle(call: suspend () -> T): T
}

internal class AuthResponseHandlerImpl(
    private val tokensInteractor: TokensInteractor,
) : AuthResponseHandler {
    override suspend fun <T> handle(call: suspend () -> T): T =
        try {
            call()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.BadRequest) {
                tokensInteractor.clearTokens()
            }
            throw e
        }
}