package com.brbx.player.view_model

import com.brbx.domain.use_case.PausePlaybackUseCase
import com.brbx.domain.use_case.ResumePlaybackUseCase
import com.brbx.player.model.PlayerIntent

internal interface PlaybackController : PlayerDelegate<PlayerIntent.Playback>

internal class PlaybackControllerImpl(
    override val mviScope: PlayerMviScope,
    private val pausePlaybackUseCase: PausePlaybackUseCase,
    private val resumePlaybackUseCase: ResumePlaybackUseCase,
) : PlaybackController {
    override fun invoke(intent: PlayerIntent.Playback) {
        when (intent) {
            PlayerIntent.Playback.Pause -> pausePlaybackUseCase()
            PlayerIntent.Playback.Resume -> resumePlaybackUseCase()
        }
    }
}