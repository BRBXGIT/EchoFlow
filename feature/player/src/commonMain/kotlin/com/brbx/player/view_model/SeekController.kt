package com.brbx.player.view_model

import com.brbx.domain.use_case.SeekToPositionUseCase
import com.brbx.domain.use_case.SkipToNextTrackUseCase
import com.brbx.domain.use_case.SkipToPreviousTrackUseCase
import com.brbx.player.model.PlayerIntent

internal interface SeekController : PlayerDelegate<PlayerIntent.Seek>

internal class SeekControllerImpl(
    override val mviScope: PlayerMviScope,
    private val seekToPositionUseCase: SeekToPositionUseCase,
    private val skipToNextTrackUseCase: SkipToNextTrackUseCase,
    private val skipToPreviousTrackUseCase: SkipToPreviousTrackUseCase,
) : SeekController {

    override fun invoke(intent: PlayerIntent.Seek) = when (intent) {
        is PlayerIntent.Seek.SeekTo -> seekToPositionUseCase(intent.positionMs)
        PlayerIntent.Seek.SkipToNext -> skipToNextTrackUseCase()
        PlayerIntent.Seek.SkipToPrevious -> skipToPreviousTrackUseCase()
    }
}
