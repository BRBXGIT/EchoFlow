package com.brbx.player.view_model

import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.player.model.PlayerIntent
import com.brbx.player.model.UiPlayerState

class PlayerViewModel : EchoFlowViewModel<UiPlayerState, PlayerIntent, Unit>(
    initialState = UiPlayerState(),
) {
    private val playbackController by injectDelegate<PlaybackController>()
    private val seekController by injectDelegate<SeekController>()

    override fun dispatchIntent(intent: PlayerIntent) {
        when (intent) {
            is PlayerIntent.Playback -> playbackController(intent)
            is PlayerIntent.Seek -> seekController(intent)
        }
    }
}