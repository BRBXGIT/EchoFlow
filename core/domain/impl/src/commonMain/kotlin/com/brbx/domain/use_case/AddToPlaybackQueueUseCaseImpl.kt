package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController
import com.brbx.domain.model.common.Track

internal class AddToPlaybackQueueUseCaseImpl(
    private val controller: PlayerController,
) : AddToPlaybackQueueUseCase {
    override fun invoke(track: Track?) =
        controller.addToQueue(track)
}