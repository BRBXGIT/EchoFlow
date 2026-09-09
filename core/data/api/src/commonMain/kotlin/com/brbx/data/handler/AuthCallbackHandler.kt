package com.brbx.data.handler

import com.brbx.domain.model.auth.AuthCallbackPayload

interface AuthCallbackHandler {
    fun handle(rawUri: String): AuthCallbackPayload?
}
