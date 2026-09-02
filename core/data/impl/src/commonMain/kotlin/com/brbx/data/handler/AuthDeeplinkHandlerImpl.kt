package com.brbx.data.handler

import com.brbx.core.data.impl.AuthConfig
import com.brbx.domain.model.AuthCallbackPayload
import io.ktor.http.Url

internal class AuthDeeplinkHandlerImpl : AuthDeeplinkHandler {
    override fun handle(rawUri: String): AuthCallbackPayload? {
        if (!rawUri.startsWith(prefix = AuthConfig.androidRedirectUri)) return null

        val url = Url(urlString = rawUri)
        val code = url.parameters[code]
        val state = url.parameters[state]

        val payload = if (code != null && state != null) {
            AuthCallbackPayload(code = code, state = state)
        } else null
        return payload
    }

    companion object {
        const val code = "code"
        const val state = "state"
    }
}