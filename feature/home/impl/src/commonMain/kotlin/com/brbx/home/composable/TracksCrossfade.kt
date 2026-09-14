package com.brbx.home.composable

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.theme.mMotion
import com.brbx.domain.model.common.Track
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun TracksCrossfade(
    loading: Boolean,
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
    loadingContent: @Composable () -> Unit,
    emptyContent: @Composable () -> Unit,
    listContent: @Composable (ImmutableList<TrackItem>) -> Unit,
) {
    val state = rememberContentState(loading, tracks)
    Crossfade(
        modifier = modifier,
        targetState = state,
        animationSpec = mMotion.nonSpatialExtraFastSpec(),
    ) { target ->
        when (target) {
            ContentState.Loading -> loadingContent()
            ContentState.Empty -> emptyContent()
            is ContentState.List -> listContent(target.tracks)
        }
    }
}

private sealed interface ContentState {
    data object Loading : ContentState
    data object Empty : ContentState
    data class List(val tracks: ImmutableList<TrackItem>) : ContentState
}

@Composable
private fun rememberContentState(loading: Boolean, tracks: ImmutableList<TrackItem>): ContentState =
    remember(key1 = loading, key2 = tracks) {
        when {
            loading -> ContentState.Loading
            tracks.isEmpty() -> ContentState.Empty
            else -> ContentState.List(tracks)
        }
    }
