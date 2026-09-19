package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController
import com.brbx.domain.model.common.Track

internal class SetPlaybackQueueUseCaseImpl(
    private val playerController: PlayerController,
) : SetPlaybackQueueUseCase {
    override fun invoke(tracks: List<Track>, startIndex: Int) =
        playerController.setQueue(tracks = tracks, startIndex = startIndex)
}
