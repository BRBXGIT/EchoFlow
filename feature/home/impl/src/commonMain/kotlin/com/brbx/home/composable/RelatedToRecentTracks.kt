package com.brbx.home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mTypography
import com.brbx.domain.model.Track

private const val RelatedToRecentTracksKey = "RelatedToRecentTracks"

internal fun LazyListScope.relatedToRecentTracks(tracks: LazyPagingItems<Track>?) =
    item(key = RelatedToRecentTracksKey) {
        RelatedToRecentTracks(
            tracks = tracks,
            modifier = Modifier
                .animateItem()
                .padding(horizontal = mDimens.micro8)
        )
    }

@Composable
private fun RelatedToRecentTracks(
    tracks: LazyPagingItems<Track>?,
    modifier: Modifier = Modifier
) {
    val count = tracks?.itemCount ?: 0
    var itemsToShow by remember { mutableIntStateOf(minOf(4, count)) }
    LaunchedEffect(count) {
        itemsToShow = minOf(4, count)
    }

    if (itemsToShow == 0) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Кажется, вы ничего не слушали,\nрекомендаций нет",
                style = mTypography.bodyMedium,
                color = mColors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(modifier = modifier) {
            for (index in 0 until itemsToShow) {
                val track = tracks?.get(index)

                if (track != null) {
                    TrackItem(
                        isPlaying = false,
                        poster = track.highResArtworkUrl,
                        title = track.title,
                        artist = track.user?.name ?: "Unknown",
                        isFirst = index == 0,
                        isLast = index == itemsToShow - 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}