package com.brbx.home.view_model

import com.brbx.domain.use_case.GetDailyMixUseCase
import com.brbx.feature_common.utils.CommonText
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.home.model.HomeIntent
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.playlist.Playlist
import com.brbx.playlist.PlaylistRoute
import echoflow.feature.home.impl.generated.resources.Res
import echoflow.feature.home.impl.generated.resources.recent_tracks_divider_label
import echoflow.feature.home.impl.generated.resources.related_to_recently_title

internal interface PlaylistOpener : HomeViewModelDelegate<HomeIntent.Playlists>

internal class PlaylistOpenerImpl(
    override val mviScope: HomeMviScope,
    private val mixUseCase: GetDailyMixUseCase,
    private val tracksStore: HomeTracksStore,
) : PlaylistOpener {
    override fun invoke(intent: HomeIntent.Playlists) {
        when (intent) {
            HomeIntent.Playlists.OpenMix -> openMix()
            HomeIntent.Playlists.OpenRecent -> openRecent()
        }
    }

    private fun openMix() = launchAction {
        val route = PlaylistRoute(
            playlist = Playlist.Paged(
                title = CommonText.Res(value = Res.string.related_to_recently_title),
                paginator = mixUseCase(),
            )
        )
        postEffect(EchoFlowEffect.Navigate(key = route))
    }

    private fun openRecent() {
        val route = PlaylistRoute(
            playlist = Playlist.ReadyList(
                title = CommonText.Res(value = Res.string.recent_tracks_divider_label),
                tracks = tracksStore.recent,
            )
        )
        postEffect(EchoFlowEffect.Navigate(key = route))
    }
}