package com.brbx.player.view_model

import com.brbx.domain.use_case.GetPlayerStateUseCase
import com.brbx.mvi_core.helpers.bindLatest
import com.brbx.player.model.PlayerIntent
import com.brbx.player.model.toUi

internal interface PlayerStateObserver : PlayerDelegate<PlayerIntent.ObservePlayerState>

internal class PlayerStateObserverImpl(
    override val scope: PlayerMviScope,
    private val getPlayerStateUseCase: GetPlayerStateUseCase,
) : PlayerStateObserver {

    init { observePlayerState() }

    override fun invoke(intent: PlayerIntent.ObservePlayerState) {
        observePlayerState()
    }

    private fun observePlayerState() =
        getPlayerStateUseCase() bindLatest { it.toUi() }
}