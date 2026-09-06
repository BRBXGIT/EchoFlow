package com.brbx.data.handler

import com.brbx.domain.model.AuthCallbackPayload

interface AuthCallbackHandler {
    fun handle(rawUri: String): AuthCallbackPayload?
}
