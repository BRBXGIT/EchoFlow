package com.brbx.playlist.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.theme.mColors
import com.brbx.feature_common.utils.asString
import com.brbx.playlist.Playlist
import com.brbx.playlist.model.PlaylistIntent
import com.brbx.playlist.view_model.PlaylistViewModel
import org.koin.compose.viewmodel.koinViewModel

internal val scaffoldContainerColor @Composable @ReadOnlyComposable get() = mColors.surface

@Composable
internal fun PlaylistScaffold(playlist: Playlist) {
    val viewModel = koinViewModel<PlaylistViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatchIntent = viewModel::dispatchIntent

    HandlePlaylist(playlist, dispatchIntent)

    Scaffold(
        containerColor = scaffoldContainerColor,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        PlaylistContent(
            scaffoldPadding = innerPadding,
            playlistName = state.title.asString(),
            tracks = state.tracks.items,
            isLoading = state.tracks.isLoading,
            dispatchIntent = dispatchIntent,
            modifier = Modifier.fillMaxSize()
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