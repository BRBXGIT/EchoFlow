package com.brbx.home.view_model

import androidx.compose.runtime.Immutable
import com.brbx.feature_common.view_model.ScopedEchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.home.model.HomeIntent
import com.brbx.home.model.HomeState

@Immutable
internal class HomeViewModel :
    ScopedEchoFlowViewModel<HomeState, HomeIntent, Unit>(initialState = HomeState()) {

    private val feedLoader by injectDelegate<FeedLoader>()
    private val playlistOpener by injectDelegate<PlaylistOpener>()
    private val homeDj by injectDelegate<HomeDj>()

    init { feedLoader }

    override fun dispatchIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Refresh -> feedLoader(intent)
            is HomeIntent.Playlists -> playlistOpener(intent)
            is HomeIntent.Player -> homeDj(intent)
        }
    }
}