package com.brbx.data.handler

import com.brbx.core.data.impl.AuthConfig
import com.brbx.domain.model.AuthCallbackPayload
import io.ktor.http.Url

internal class AuthCallbackHandlerImpl : AuthCallbackHandler {
    override fun handle(rawUri: String): AuthCallbackPayload? {
        val jvmUri = "${AuthConfig.jvmScheme}${AuthConfig.jvmHost}:${AuthConfig.jvmPort}${AuthConfig.jvmPath}"
        val isUriCorrect = rawUri.startsWith(prefix = AuthConfig.androidRedirectUri)
                || rawUri.startsWith(prefix = jvmUri)
        if (!isUriCorrect) return null

        val url = Url(urlString = rawUri)
        val code = url.parameters[code]
        val state = url.parameters[state]

        return if (code != null && state != null) {
            AuthCallbackPayload(code = code, state = state)
        } else null
    }

    private companion object {
        const val code = "code"
        const val state = "state"
    }
}
