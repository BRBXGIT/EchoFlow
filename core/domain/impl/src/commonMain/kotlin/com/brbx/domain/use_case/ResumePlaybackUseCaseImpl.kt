package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController

internal class ResumePlaybackUseCaseImpl(
    private val playerController: PlayerController,
) : ResumePlaybackUseCase {
    override fun invoke() = playerController.resume()
}
