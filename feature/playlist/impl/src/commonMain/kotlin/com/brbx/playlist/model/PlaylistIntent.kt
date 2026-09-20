package com.brbx.playlist.model

import com.brbx.playlist.Playlist

internal sealed interface PlaylistIntent {
    @JvmInline value class BindPlaylist(val playlist: Playlist) : PlaylistIntent
}