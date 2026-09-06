package com.brbx.data.builder

import com.brbx.core.data.impl.AuthConfig

internal expect fun AuthUrlBuilderImpl.platformState(state: String): String

internal class AuthUrlBuilderImpl(
    private val pkceGenerator: PkceGenerator,
) : AuthUrlBuilder {
    private var cachedUrl: String? = null

    override var currentCodeVerifier: String? = null
    override var currentState: String? = null

    override fun buildUrl(): String = cachedUrl ?: generateUrl().also { cachedUrl = it }

    override fun clear() {
        cachedUrl = null
        currentCodeVerifier = null
        currentState = null
    }

    private fun generateUrl(): String = run {
        val state = pkceGenerator.generateRandomString()
        val codeVerifier = pkceGenerator.generateRandomString(length = VERIFIER_LENGTH)
        val codeChallenge = pkceGenerator.generateCodeChallenge(codeVerifier)

        currentCodeVerifier = codeVerifier
        currentState = platformState(state)

        buildString {
            append(AuthConfig.authBasePath)
            append("?$PARAM_CLIENT_ID=${AuthConfig.clientId}")
            append("&$PARAM_REDIRECT_URI=${AuthConfig.androidRedirectUri}")
            append("&$PARAM_RESPONSE_TYPE=$VALUE_RESPONSE_TYPE_CODE")
            append("&$PARAM_CODE_CHALLENGE_METHOD=$VALUE_CODE_CHALLENGE_METHOD")
            append("&$PARAM_CODE_CHALLENGE=$codeChallenge")
            append("&$PARAM_STATE=$currentState")
        }
    }

    private companion object {
        const val PARAM_CLIENT_ID = "client_id"
        const val PARAM_REDIRECT_URI = "redirect_uri"
        const val PARAM_RESPONSE_TYPE = "response_type"
        const val PARAM_CODE_CHALLENGE_METHOD = "code_challenge_method"
        const val PARAM_CODE_CHALLENGE = "code_challenge"
        const val PARAM_STATE = "state"

        const val VALUE_RESPONSE_TYPE_CODE = "code"
        const val VALUE_CODE_CHALLENGE_METHOD = "S256"

        const val VERIFIER_LENGTH = 64
    }
}
