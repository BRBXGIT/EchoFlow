package com.brbx.network.api

import com.brbx.network.client.AuthApiClientProvider
import com.brbx.network.model.AuthRequestDto
import com.brbx.network.model.AuthResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters

internal class AuthApiImpl(
    private val clientProvider: AuthApiClientProvider,
) : AuthApi {
    override suspend fun exchangeCode(authRequest: AuthRequestDto): AuthResponseDto =
        clientProvider.client.submitForm(
            url = EndPoint,
            formParameters = authRequest.toFormParameters()
        ).body()

    private fun AuthRequestDto.toFormParameters() = Parameters.build {
        append(name = "code", value = code)
        append(name = "code_verifier", value = codeVerifier)
        append(name = "client_id", value = clientId)
        append(name = "client_secret", value = clientSecret)
        append(name = "redirect_uri", value = redirectUri)
        append(name = "grant_type", value = grantType)
    }

    private companion object {
        const val EndPoint = "oauth/token"
    }
}