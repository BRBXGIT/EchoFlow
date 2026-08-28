package com.brbx.data.builder

import com.brbx.core.data.impl.AuthConfig

internal class AuthLinkBuilderImpl(
    private val pkceGenerator: PkceGenerator,
) : AuthLinkBuilder {
    private var cachedLink: String? = null

    override var currentCodeVerifier: String? = null

    override fun getLink(): String {
        return cachedLink ?: generateLink().also { cachedLink = it }
    }

    private fun generateLink(): String {
        val state = pkceGenerator.generateRandomString()
        val codeVerifier = pkceGenerator.generateRandomString(length = verifierLength)
        val codeChallenge = pkceGenerator.generateCodeChallenge(codeVerifier)

        this.currentCodeVerifier = codeVerifier

        return buildString {
            append(AuthConfig.authBasePath)
            append("?$paramClientId=${AuthConfig.clientId}")
            append("&$paramRedirectUri=${AuthConfig.redirectUri}")
            append("&$paramResponseType=$valueResponseTypeCode")
            append("&$paramCodeChallengeMethod=$valueCodeChallengeMethod")
            append("&$paramCodeChallenge=$codeChallenge")
            append("&$paramState=$state")
        }
    }

    private companion object {
        // Параметры запроса
        const val paramClientId = "client_id"
        const val paramRedirectUri = "redirect_uri"
        const val paramResponseType = "response_type"
        const val paramCodeChallengeMethod = "code_challenge_method"
        const val paramCodeChallenge = "code_challenge"
        const val paramState = "state"

        const val valueResponseTypeCode = "code"
        const val valueCodeChallengeMethod = "S256"

        const val verifierLength = 64
    }
}