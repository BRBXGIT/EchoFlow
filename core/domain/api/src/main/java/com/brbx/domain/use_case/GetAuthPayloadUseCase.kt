package com.brbx.domain.use_case

import com.brbx.domain.model.AuthCallbackPayload

fun interface GetAuthPayloadUseCase {
    operator fun invoke(rawUri: String): AuthCallbackPayload?
}