package com.brbx.playlist.view_model

import com.brbx.mvi_core.helpers.launchActionIf
import com.brbx.playlist.Playlist
import com.brbx.playlist.model.PlaylistIntent
import kotlinx.coroutines.CoroutineDispatcher

internal interface PlaylistLoader : PlaylistViewModelDelegate<PlaylistIntent.LoadNext>

internal class PlaylistLoaderImpl(
    override val mviScope: PlaylistMviScope,
    private val playlistStore: PlaylistStore,
    private val defaultDispatcher: CoroutineDispatcher,
) : PlaylistLoader {
    override fun invoke(intent: PlaylistIntent.LoadNext) {
        loadNext(playlistStore.playlist)
    }

    private fun loadNext(playlist: PlaylistType?) =
        launchActionIf(
            condition = playlist is PlaylistType.Paged,
            context = defaultDispatcher,
        ) { (playlist as PlaylistType.Paged).paginator.loadNext() }
}