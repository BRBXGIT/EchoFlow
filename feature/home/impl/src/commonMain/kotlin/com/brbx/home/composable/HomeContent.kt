package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowScreenPreview
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.components.utils.rememberIsLargeScreen
import com.brbx.design_system.theme.mDimens
import com.brbx.home.model.HomeIntent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun HomeContent(
    recentLoading: Boolean,
    relatedLoading: Boolean,
    relatedToRecentTracks: ImmutableList<TrackItem>,
    recentTracks: ImmutableList<TrackItem>,
    recentlyPosters: ImmutableList<String?>,
    dispatchIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLarge = rememberIsLargeScreen()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.macro3),
        contentPadding = PaddingValues(vertical = mDimens.macro3),
        modifier = modifier,
    ) {
        if (isLarge) {
            largeRecentSection(
                recentLoading, relatedLoading,
                relatedToRecentTracks, recentTracks,
                recentlyPosters, dispatchIntent
            )
        } else {
            relatedToRecentTracks(relatedLoading, relatedToRecentTracks, recentlyPosters, dispatchIntent)

            recentTracks(recentLoading, recentTracks, dispatchIntent)
        }
    }
}

private val PreviewTracks = persistentListOf(
    TrackItem(
        id = 1L,
        title = "Midnight City",
        poster = null,
        artist = "M83",
    ),
    TrackItem(
        id = 2L,
        title = "Starboy",
        poster = null,
        artist = "The Weeknd",
    ),
    TrackItem(
        id = 3L,
        title = "Get Lucky",
        poster = null,
        artist = "Daft Punk",
    ),
    TrackItem(
        id = 4L,
        title = "Resonance",
        poster = null,
        artist = "HOME",
    ),
)

@Composable
@EchoFlowScreenPreview
private fun HomeContentWithItemsPreview() =
    HomeContent(
        recentLoading = false,
        relatedLoading = false,
        relatedToRecentTracks = PreviewTracks,
        recentTracks = PreviewTracks,
        recentlyPosters = persistentListOf(null, null, null),
        dispatchIntent = {},
    )

@Composable
@EchoFlowScreenPreview
private fun HomeContentShimmerPreview() =
    HomeContent(
        recentLoading = true,
        relatedLoading = true,
        relatedToRecentTracks = persistentListOf(),
        recentTracks = persistentListOf(),
        recentlyPosters = persistentListOf(),
        dispatchIntent = {},
    )

@Composable
@EchoFlowScreenPreview
private fun HomeContentEmptyPreview() =
    HomeContent(
        recentLoading = false,
        relatedLoading = false,
        relatedToRecentTracks = persistentListOf(),
        recentTracks = persistentListOf(),
        recentlyPosters = persistentListOf(),
        dispatchIntent = {},
    )


