package com.brbx.network.api

import com.brbx.core.network.impl.AuthConfig
import com.brbx.network.client.AuthApiClientProvider
import com.brbx.network.model.AuthRequestDto
import com.brbx.network.model.AuthResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

internal class AuthApiImpl(
    private val clientProvider: AuthApiClientProvider,
) : AuthApi {

    override suspend fun exchangeCode(authRequest: AuthRequestDto): AuthResponseDto =
        postTokenForm(parameters = authRequest.toFormParameters())

    override suspend fun refreshTokens(refreshToken: String): AuthResponseDto =
        postTokenForm(parameters = refreshForm(refreshToken))

    private suspend fun postTokenForm(parameters: Parameters): AuthResponseDto =
        clientProvider.client.submitForm(
            url = TokenEndpoint,
            formParameters = parameters,
        ).body()

    private fun AuthRequestDto.toFormParameters(): Parameters = formParametersOf(
        "code" to code,
        "code_verifier" to codeVerifier,
        "client_id" to clientId,
        "client_secret" to clientSecret,
        "redirect_uri" to redirectUri,
        "grant_type" to grantType,
    )

    private fun refreshForm(refreshToken: String): Parameters = formParametersOf(
        "grant_type" to AuthConfig.refreshGrantType,
        "client_id" to AuthConfig.clientId,
        "client_secret" to AuthConfig.clientSecret,
        "refresh_token" to refreshToken,
    )

    private companion object {
        const val TokenEndpoint = "oauth/token"
    }
}

private fun formParametersOf(vararg pairs: Pair<String, String?>): Parameters = Parameters.build {
    pairs.forEach { (key, value) ->
        if (value != null) append(name = key, value = value)
    }
}