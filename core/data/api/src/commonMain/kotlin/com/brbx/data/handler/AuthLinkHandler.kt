package com.brbx.data.handler

import com.brbx.domain.model.AuthCallbackPayload

interface AuthLinkHandler {
    fun handle(rawUri: String): AuthCallbackPayload?
}