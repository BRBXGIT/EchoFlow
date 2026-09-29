package com.brbx.playlist.model

import androidx.compose.runtime.Immutable
import com.brbx.playlist.Playlist

@Immutable
internal sealed interface PlaylistIntent {
    @JvmInline value class BindPlaylist(val playlist: Playlist) : PlaylistIntent

    data object LoadNext : PlaylistIntent

    @JvmInline value class PlayPlaylist(val trackIndex: Int) : PlaylistIntent
}