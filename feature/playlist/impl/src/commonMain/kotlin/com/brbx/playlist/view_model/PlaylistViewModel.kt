package com.brbx.playlist.view_model

import com.brbx.feature_common.view_model.ScopedEchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.playlist.model.PlaylistIntent
import com.brbx.playlist.model.PlaylistState

internal class PlaylistViewModel :
    ScopedEchoFlowViewModel<PlaylistState, PlaylistIntent, Unit>(initialState = PlaylistState()) {

    private val playlistBinder by injectDelegate<PlaylistBinder>()
    private val playlistLoader by injectDelegate<PlaylistLoader>()
    private val playlistDj by injectDelegate<PlaylistDj>()

    override fun dispatchIntent(intent: PlaylistIntent) =
        when (intent) {
            is PlaylistIntent.BindPlaylist -> playlistBinder(intent)
            is PlaylistIntent.LoadNext -> playlistLoader(intent)
            is PlaylistIntent.PlayPlaylist -> playlistDj(intent)
        }
}