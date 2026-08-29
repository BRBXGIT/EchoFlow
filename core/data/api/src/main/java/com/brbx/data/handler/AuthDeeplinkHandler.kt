package com.brbx.data.handler

import com.brbx.domain.model.AuthCallbackPayload

interface AuthDeeplinkHandler {
    fun handle(rawUri: String): AuthCallbackPayload?
}