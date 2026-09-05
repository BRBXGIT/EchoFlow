package com.brbx.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.brbx.design_system.components.components.MediaCard
import com.brbx.design_system.theme.mDimens
import com.brbx.domain.model.Track
import kotlinx.collections.immutable.ImmutableList

private const val RecentlyPlayedKey = "RecentlyPlayedKey"

internal fun LazyListScope.recentlyPlayedTracks(tracks: ImmutableList<Track>) =
    item(key = RecentlyPlayedKey) {
        LatestTracksRow(tracks, Modifier.animateItem())
    }

@Composable
private fun LatestTracksRow(
    tracks: ImmutableList<Track>,
    modifier: Modifier = Modifier,
) =
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(mDimens.micro8),
        contentPadding = PaddingValues(horizontal = mDimens.micro8),
    ) {
        items(tracks, key = { it.id }) { track ->
            MediaCard(
                modifier = Modifier.animateItem(),
                title = track.title,
                description = track.user?.name,
                poster = track.highResArtworkUrl,
            )
        }
    }