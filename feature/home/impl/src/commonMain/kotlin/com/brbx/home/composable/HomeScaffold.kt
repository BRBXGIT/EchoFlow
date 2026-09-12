package com.brbx.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.theme.mColors
import com.brbx.home.view_model.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

private const val RecentSnapshotCount = 15
internal const val RelatedToRecentlySnapshotCount = 4

@Composable
internal fun HomeScaffold() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = mColors.surface,
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        HomeContent(
            recentLoading = state.recentlyListened.isLoading,
            relatedLoading = state.relatedToRecently.isLoadingOrRefreshing,
            recentTracks = state.recentlyListened.collectionSnapshot(n = RecentSnapshotCount),
            relatedToRecentTracks = state.relatedToRecently.itemsSnapshot(n = RelatedToRecentlySnapshotCount),
            recentlyPosters = state.recentlyPosters,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
        )
    }
}