package com.brbx.playlist.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.feature_common.utils.asString
import com.brbx.playlist.Playlist
import com.brbx.playlist.model.PlaylistIntent
import com.brbx.playlist.view_model.PlaylistViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PlaylistScaffold(playlist: Playlist) {
    val viewModel = koinViewModel<PlaylistViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HandlePlaylist(playlist, viewModel::dispatchIntent)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        PlaylistContent(
            playlistName = state.title.asString(),
            tracks = state.tracks,
            onPlayClick = {},
            onMixClick = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        )
    }
}

@Composable
private fun HandlePlaylist(
    playlist: Playlist,
    dispatchIntent: (PlaylistIntent) -> Unit,
) =
    LaunchedEffect(key1 = playlist) {
        dispatchIntent(PlaylistIntent.BindPlaylist(playlist))
    }