package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController

internal class PausePlaybackUseCaseImpl(
    private val playerController: PlayerController,
) : PausePlaybackUseCase {
    override fun invoke() = playerController.pause()
}
