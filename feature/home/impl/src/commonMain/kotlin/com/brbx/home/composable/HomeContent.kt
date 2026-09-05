package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brbx.design_system.theme.mDimens
import com.brbx.domain.model.Track
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeContent(
    recentlyPlayed: ImmutableList<Track>,
    modifier: Modifier = Modifier,
) =
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.micro8),
        contentPadding = PaddingValues(vertical = mDimens.micro8),
        modifier = modifier,
    ) {
        recentlyPlayedTracks(recentlyPlayed)
    }