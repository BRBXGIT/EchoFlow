package com.brbx.domain.use_case

import com.brbx.data.handler.AuthDeeplinkHandler
import com.brbx.domain.model.AuthCallbackPayload

internal class GetAuthPayloadUseCaseImpl(
    private val handler: AuthDeeplinkHandler,
) : GetAuthPayloadUseCase {
    override fun invoke(rawUri: String): AuthCallbackPayload? =
        handler.handle(rawUri)
}