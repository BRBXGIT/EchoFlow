package com.brbx.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.components.components.PlaylistSheet
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.theme.mColors
import com.brbx.home.model.HomeIntent
import com.brbx.home.view_model.HomeViewModel
import echoflow.feature.home.impl.generated.resources.Res
import echoflow.feature.home.impl.generated.resources.recent_tracks_divider_label
import echoflow.feature.home.impl.generated.resources.related_to_recently_title
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private const val RecentSnapshotCount = 15
internal const val RelatedToRecentlySnapshotCount = 4

@Composable
internal fun HomeScaffold() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatchIntent = viewModel::dispatchIntent

    Sheets(
        dispatchIntent = dispatchIntent,
        todayMixVisible = state.todayMixVisible,
        todayMix = state.relatedToRecently.items,
        recentVisible = state.recentSheetsVisible,
        recent = state.recentlyListened.collection
    )

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = { HomeTopBar(scrollBehavior) },
        containerColor = mColors.surface,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        HomeContent(
            recentLoading = state.recentlyListened.isLoading,
            relatedLoading = state.relatedToRecently.isLoadingOrRefreshing,
            recentTracks = state.recentlyListened.collectionSnapshot(n = RecentSnapshotCount),
            relatedToRecentTracks = state.relatedToRecently.itemsSnapshot(n = RelatedToRecentlySnapshotCount),
            recentlyPosters = state.recentlyPosters,
            dispatchIntent = dispatchIntent,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
        )
    }
}

@Composable
private fun Sheets(
    dispatchIntent: (HomeIntent) -> Unit,
    todayMixVisible: Boolean,
    todayMix: ImmutableList<TrackItem>,
    recentVisible: Boolean,
    recent: ImmutableList<TrackItem>
) {
    PlaylistSheet(
        onDismissRequest = { dispatchIntent(HomeIntent.Sheets.ToggleMixSheet) },
        visible = todayMixVisible,
        playlistName = stringResource(Res.string.related_to_recently_title),
        tracks = todayMix,
    )
    PlaylistSheet(
        onDismissRequest = { dispatchIntent(HomeIntent.Sheets.ToggleRecentSheet) },
        visible = recentVisible,
        playlistName = stringResource(Res.string.recent_tracks_divider_label),
        tracks = recent,
    )
}