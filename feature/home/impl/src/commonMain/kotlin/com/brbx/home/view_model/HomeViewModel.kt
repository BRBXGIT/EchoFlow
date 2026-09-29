package com.brbx.home.view_model

import androidx.compose.runtime.Immutable
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.home.model.HomeIntent
import com.brbx.home.model.HomeState
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

@Immutable
internal class HomeViewModel :
    EchoFlowViewModel<HomeState, HomeIntent, Unit>(initialState = HomeState()),
    KoinScopeComponent {

    override val scope: Scope by lazy { createScope(source = this) }

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

    override fun onCleared() {
        super.onCleared()
        scope.close()
    }
}