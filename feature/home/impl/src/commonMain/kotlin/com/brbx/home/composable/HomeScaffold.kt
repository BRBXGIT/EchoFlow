package com.brbx.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.components.utils.rememberIsLargeScreen
import com.brbx.design_system.components.utils.rememberSnackbarHost
import com.brbx.design_system.theme.mColors
import com.brbx.feature_common.composable.HandleEchoFlowEffects
import com.brbx.home.view_model.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

private val RecentSnapshotCount
    @Composable get() = if (rememberIsLargeScreen()) 25 else 15
internal const val RelatedToRecentlySnapshotCount = 4

@Composable
internal fun HomeScaffold() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatchIntent = viewModel::dispatchIntent

    val hostState = rememberSnackbarHost()
    HandleEchoFlowEffects(viewModel.effects, snackbarHost = hostState)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
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