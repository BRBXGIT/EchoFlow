package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController

internal class SkipToPreviousTrackUseCaseImpl(
    private val playerController: PlayerController,
) : SkipToPreviousTrackUseCase {
    override fun invoke() = playerController.skipToPrevious()
}
