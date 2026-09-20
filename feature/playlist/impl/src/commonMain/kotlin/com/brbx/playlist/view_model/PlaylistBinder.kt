package com.brbx.playlist.view_model

import com.brbx.feature_common.model.toUi
import com.brbx.mvi_core.helpers.reduce
import com.brbx.playlist.Playlist
import com.brbx.playlist.model.PlaylistIntent

internal interface PlaylistBinder : PlaylistViewModelDelegate<PlaylistIntent.BindPlaylist>

internal class PlaylistBinderImpl(
    override val scope: PlaylistMviScope,
) : PlaylistBinder {
    override fun invoke(intent: PlaylistIntent.BindPlaylist) {
        bindPlaylist(intent.playlist)
    }

    private fun bindPlaylist(playlist: Playlist) =
        when (playlist) {
            is Playlist.Id -> { /* TODO */ }
            is Playlist.Paged -> {
                val mapped = playlist.paginator.state.value.toUi()
                reduce { copy(tracks = mapped.items, title = playlist.title) }
            }
            is Playlist.List -> {
                reduce { copy(tracks = playlist.tracks, title = playlist.title) }
            }
        }
}