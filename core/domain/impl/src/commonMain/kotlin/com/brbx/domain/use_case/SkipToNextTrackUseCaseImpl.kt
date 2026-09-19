package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController

internal class SkipToNextTrackUseCaseImpl(
    private val playerController: PlayerController,
) : SkipToNextTrackUseCase {
    override fun invoke() = playerController.skipToNext()
}
