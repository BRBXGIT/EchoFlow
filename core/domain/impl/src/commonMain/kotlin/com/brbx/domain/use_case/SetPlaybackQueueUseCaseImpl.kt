package com.brbx.domain.use_case

import com.brbx.data.controller.PlayerController
import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator

internal class SetPlaybackQueueUseCaseImpl(
    private val playerController: PlayerController,
) : SetPlaybackQueueUseCase {
    override fun invoke(tracks: List<Track>, startIndex: Int) =
        playerController.setQueue(tracks = tracks, startIndex = startIndex)

    override fun invoke(paginator: Paginator<Track>, startIndex: Int) {
        // TODO Add overload logic for paged tracks
        playerController.setQueue(paginator.state.value.items, startIndex)
    }
}
