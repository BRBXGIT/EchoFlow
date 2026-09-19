package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController
import com.brbx.domain.model.PlayerState
import kotlinx.coroutines.flow.StateFlow

internal class ObservePlayerStateUseCaseImpl(
    private val playerController: PlayerController,
) : ObservePlayerStateUseCase {
    override fun invoke(): StateFlow<PlayerState> = playerController.playerState
}
