package com.brbx.playlist.view_model

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator
import com.brbx.feature_common.model.UiPaginationState
import com.brbx.feature_common.model.toUi
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.reduce
import com.brbx.playlist.Playlist
import com.brbx.playlist.model.PlaylistIntent
import kotlinx.coroutines.CoroutineDispatcher

internal interface PlaylistBinder : PlaylistViewModelDelegate<PlaylistIntent>

internal class PlaylistBinderImpl(
    override val scope: PlaylistMviScope,
    private val dispatcherDefault: CoroutineDispatcher,
) : PlaylistBinder {

    private var paginator: Paginator<Track>? = null

    override fun invoke(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.BindPlaylist -> bindPlaylist(intent.playlist)
            PlaylistIntent.LoadNext -> loadNext()
        }
    }

    private fun bindPlaylist(playlist: Playlist) =
        when (playlist) {
            is Playlist.Id -> { /* TODO */ }
            is Playlist.Paged -> {
                val mapped = playlist.paginator.also { paginator = it }.state.value.toUi()
                reduce { copy(tracks = mapped, title = playlist.title) }
            }
            is Playlist.List -> {
                val emptyPagination = UiPaginationState(
                    items = playlist.tracks,
                    endReached = true,
                )
                reduce { copy(tracks = emptyPagination, title = playlist.title) }
            }
        }

    private fun loadNext() = paginator?.let {
        launchAction(context = dispatcherDefault) { it.loadNext() }
    }
}