package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mDimens
import com.brbx.domain.model.common.Track
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeContent(
    recentLoading: Boolean,
    relatedLoading: Boolean,
    relatedToRecentTracks: ImmutableList<Track>,
    recentTracks: ImmutableList<Track>,
    recentlyPosters: ImmutableList<String?>,
    modifier: Modifier = Modifier,
) =
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.micro8),
        contentPadding = PaddingValues(vertical = mDimens.micro8),
        modifier = modifier,
    ) {
        relatedToRecentTracks(relatedLoading, relatedToRecentTracks, recentlyPosters)

        recentTracks(recentLoading, recentTracks)
    }