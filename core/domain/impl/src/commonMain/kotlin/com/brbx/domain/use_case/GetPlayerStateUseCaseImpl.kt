package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController
import com.brbx.domain.model.PlayerState
import kotlinx.coroutines.flow.StateFlow

internal class GetPlayerStateUseCaseImpl(
    private val playerController: PlayerController,
) : GetPlayerStateUseCase {
    override fun invoke(): StateFlow<PlayerState> = playerController.playerState
}
