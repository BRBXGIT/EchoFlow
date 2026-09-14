package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.theme.mDimens
import com.brbx.domain.model.common.Track
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeContent(
    recentLoading: Boolean,
    relatedLoading: Boolean,
    relatedToRecentTracks: ImmutableList<TrackItem>,
    recentTracks: ImmutableList<TrackItem>,
    recentlyPosters: ImmutableList<String?>,
    modifier: Modifier = Modifier,
) =
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.macro3),
        contentPadding = PaddingValues(vertical = mDimens.macro3),
        modifier = modifier,
    ) {
        relatedToRecentTracks(relatedLoading, relatedToRecentTracks, recentlyPosters)

        recentTracks(recentLoading, recentTracks)
    }