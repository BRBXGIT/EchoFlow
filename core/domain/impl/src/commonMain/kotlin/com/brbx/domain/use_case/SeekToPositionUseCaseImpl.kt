package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController

internal class SeekToPositionUseCaseImpl(
    private val playerController: PlayerController,
) : SeekToPositionUseCase {
    override fun invoke(positionMs: Long) = playerController.seekTo(positionMs = positionMs)
}
