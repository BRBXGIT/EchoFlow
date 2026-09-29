package com.brbx.playlist.view_model

import com.brbx.domain.use_case.SetPlaybackQueueUseCase
import com.brbx.playlist.model.PlaylistIntent

internal interface PlaylistDj : PlaylistViewModelDelegate<PlaylistIntent.PlayPlaylist>

internal class PlaylistDjImpl(
    override val mviScope: PlaylistMviScope,
    private val playlistStore: PlaylistStore,
    private val setQueueUseCase: SetPlaybackQueueUseCase,
) : PlaylistDj {
    override fun invoke(intent: PlaylistIntent.PlayPlaylist) {
        playPlaylist(intent.trackIndex)
    }

    private fun playPlaylist(index: Int) =
        when (val playlist = playlistStore.playlist) {
            is PlaylistType.Paged -> setQueueUseCase(playlist.paginator, startIndex = index)
            is PlaylistType.ReadyList -> setQueueUseCase(playlist.tracks, startIndex = index)
            null -> {}
        }
}