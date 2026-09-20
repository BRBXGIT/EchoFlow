package com.brbx.playlist.view_model

import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.playlist.model.PlaylistIntent
import com.brbx.playlist.model.PlaylistState

internal class PlaylistViewModel : EchoFlowViewModel<PlaylistState, PlaylistIntent, Unit>(
    initialState = PlaylistState(),
) {
    private val playlistBinder by injectDelegate<PlaylistBinder>()

    override fun dispatchIntent(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.BindPlaylist -> playlistBinder(intent)
        }
    }
}