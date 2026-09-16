package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.theme.mDimens
import com.brbx.home.model.HomeIntent
import kotlinx.collections.immutable.ImmutableList

private const val LargeRecentSectionKey = "LargeRecentSectionKey"

internal fun LazyListScope.largeRecentSection(
    recentLoading: Boolean,
    relatedLoading: Boolean,
    relatedToRecentTracks: ImmutableList<TrackItem>,
    recentTracks: ImmutableList<TrackItem>,
    recentlyPosters: ImmutableList<String?>,
    dispatchIntent: (HomeIntent) -> Unit,
) =
    item(LargeRecentSectionKey) {
        LargeScreenRecentSection(
            recentLoading = recentLoading,
            relatedLoading = relatedLoading,
            relatedToRecentTracks = relatedToRecentTracks,
            recentTracks = recentTracks,
            recentlyPosters = recentlyPosters,
            dispatchIntent = dispatchIntent,
            modifier = Modifier
                .animateItem()
                .padding(horizontal = mDimens.micro8)
                .height(IntrinsicSize.Max),
        )
    }

@Composable
private fun LargeScreenRecentSection(
    recentLoading: Boolean,
    relatedLoading: Boolean,
    relatedToRecentTracks: ImmutableList<TrackItem>,
    recentTracks: ImmutableList<TrackItem>,
    recentlyPosters: ImmutableList<String?>,
    dispatchIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) =
    Row(
        horizontalArrangement = Arrangement.spacedBy(mDimens.micro8),
        modifier = modifier,
    ) {
        RelatedToRecentTracks(
            relatedLoading = relatedLoading,
            tracks = relatedToRecentTracks,
            posters = recentlyPosters,
            dispatchIntent = dispatchIntent,
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
        )

        RecentTracks(
            recentLoading = recentLoading,
            tracks = recentTracks,
            dispatchIntent = dispatchIntent,
            rowCount = 5,
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
        )
    }