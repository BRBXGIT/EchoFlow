package com.brbx.playlist.view_model

import com.brbx.domain.model.common.Track
import com.brbx.domain.pagination.Paginator
import com.brbx.feature_common.model.UiPaginationState
import com.brbx.feature_common.model.toUi
import com.brbx.mvi_core.helpers.bindLatest
import com.brbx.mvi_core.helpers.reduce
import com.brbx.playlist.Playlist
import com.brbx.playlist.model.PlaylistIntent

internal interface PlaylistBinder : PlaylistViewModelDelegate<PlaylistIntent.BindPlaylist>

internal class PlaylistBinderImpl(
    override val mviScope: PlaylistMviScope,
    private val playlistStore: PlaylistStore,
) : PlaylistBinder {

    override fun invoke(intent: PlaylistIntent.BindPlaylist) {
        bindPlaylist(intent.playlist)
    }

    private fun bindPlaylist(playlist: Playlist) {
        when (playlist) {
            is Playlist.Id -> { /* TODO */ }
            is Playlist.Paged -> {
                playlist.paginator.addToStoreAsPaged()
                playlist.paginator.state.bindLatest { paged ->
                    copy(tracks = paged.toUi(), title = playlist.title)
                }
            }
            is Playlist.ReadyList -> {
                val emptyPagination = UiPaginationState(
                    items = playlist.tracks.toUi(),
                    endReached = true,
                )
                addToStoreAsList(tracks = playlist.tracks)
                reduce { copy(tracks = emptyPagination, title = playlist.title) }
            }
        }
    }

    private fun Paginator<Track>.addToStoreAsPaged() {
        playlistStore.playlist = PlaylistType.Paged(paginator = this)
    }

    private fun addToStoreAsList(tracks: List<Track>) {
        playlistStore.playlist = PlaylistType.ReadyList(tracks)
    }
}
