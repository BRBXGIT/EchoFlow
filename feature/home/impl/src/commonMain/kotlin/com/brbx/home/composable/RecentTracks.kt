package com.brbx.home.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowRemoteImage
import com.brbx.design_system.components.components.EllipsedText
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mMotion
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.domain.model.common.Track
import echoflow.core.design_system.components.generated.resources.DesignComponentsRes
import echoflow.core.design_system.components.generated.resources.unknown_artist_label
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

private val ItemPadding: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro5
private val ItemSpacing: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro4
private val ItemPosterSize
    @Composable @ReadOnlyComposable get() = mDimens.macro5

private val ItemShape: Shape get() = CircleShape
private val ItemPosterShape: Shape get() = CircleShape

private val randomLowerChar = ('a'..'z').random()
private val randomUpperChar = ('A'..'Z').random()

private const val RecentTracksKey = "RecentTracksKey"

internal fun LazyListScope.recentTracks(
    recentLoading: Boolean,
    tracks: ImmutableList<Track>,
) =
    item(key = RecentTracksKey) {
        RecentTracks(
            recentLoading = recentLoading,
            tracks = tracks,
            modifier = Modifier
                .animateItem()
                .fillMaxWidth()
        )
    }

@Composable
private fun RecentTracks(
    recentLoading: Boolean,
    tracks: ImmutableList<Track>,
    modifier: Modifier = Modifier,
) {
    val contentModifier = remember { Modifier.fillMaxWidth() }
    TracksCrossfade(
        modifier = modifier,
        loading = recentLoading,
        tracks = tracks,
        emptyContent = { Empty() },
        listContent = { tracks -> RecentTracksContainer(contentModifier) { tracks(tracks) } },
        loadingContent = {
            RecentTracksContainer(
                withScroll = false,
                modifier = contentModifier,
            ) { loading() }
        },
    )
}

// TODO Rewrite to something more interesting
@Composable
private fun Empty() =
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = mDimens.micro8)
    ) {
        Text(
            text = "Nothing here you did not listen music"
        )
    }

private fun LazyStaggeredGridScope.tracks(tracks: ImmutableList<Track>) =
    itemsIndexed(tracks) { index, track ->
        RecentTrackItem(
            title = track.title,
            poster = track.highResArtworkUrl,
            artist = track.user?.name,
            modifier = Modifier
                .animateItem()
                .width(calculateTrackContentWidth(key = index)),
        )
    }

private fun LazyStaggeredGridScope.loading() =
    items(count = 3, key = { it }) { index ->
        RecentTrackItemShimmer(
            modifier = Modifier
                .animateItem()
                .width(calculateTrackContentWidth(key = index))
        )
    }

@Composable
private fun RecentTracksContainer(
    modifier: Modifier = Modifier,
    withScroll: Boolean = true,
    content: LazyStaggeredGridScope.() -> Unit,
) =
    LazyHorizontalStaggeredGrid(
        userScrollEnabled = withScroll,
        rows = StaggeredGridCells.Fixed(count = 3),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = mDimens.micro8),
        horizontalItemSpacing = mDimens.micro8,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro8),
        content = content,
    )

private fun calculateTrackContentWidth(key: Int): Dp {
    val finalKey = "$randomLowerChar-$randomUpperChar-$key"
    val hash = abs(n = finalKey.hashCode())
    val minWidth = 100
    val maxWidth = 190
    val widthPx = minWidth + (hash % (maxWidth - minWidth + 1))
    return widthPx.dp
}

@Composable
private fun RecentTrackItem(
    title: String,
    poster: String?,
    artist: String?,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
) =
    RecentTrackItemContainer(
        isPlaying = isPlaying,
        modifier = modifier,
    ) {
        RecentTrackItemContent(
            isPlaying = isPlaying,
            poster = poster,
            title = title,
            artist = artist ?: stringResource(resource = DesignComponentsRes.string.unknown_artist_label),
        )
    }

@Composable
private fun RecentTrackItemShimmer(
    modifier: Modifier = Modifier,
) =
    RecentTrackItemShimmerContainer(modifier) {
        RecentTrackItemShimmerContent()
    }

@Composable
private fun RecentTrackItemContainer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val backgroundColorState by animateColorAsState(
        targetValue = if (isPlaying) mColors.primary else mColors.secondary,
        animationSpec = mMotion.nonSpatialFastSpec(),
    )

    Box(
        modifier = modifier
            .clip(shape = ItemShape)
            .background(color = backgroundColorState)
    ) {
        content()
    }
}

@Composable
private fun RecentTrackItemContent(
    isPlaying: Boolean,
    poster: String?,
    title: String,
    artist: String,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier.padding(all = ItemPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = ItemSpacing),
    ) {
        val textColorState by animateColorAsState(
            targetValue = if (isPlaying) mColors.onPrimary else mColors.onSecondary,
            animationSpec = mMotion.nonSpatialFastSpec(),
        )

        EchoFlowRemoteImage(
            model = poster,
            modifier = Modifier
                .size(size = ItemPosterSize)
                .clip(shape = ItemPosterShape),
        )

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            EllipsedText(
                text = title,
                style = mTypography.labelLarge.copy(
                    fontWeight = FontWeight.W600,
                    color = textColorState,
                ),
            )
            EllipsedText(
                text = artist,
                style = mTypography.labelSmall.copy(
                    color = textColorState.copy(alpha = 0.7f),
                ),
            )
        }
    }

@Composable
private fun RecentTrackItemShimmerContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) =
    Box(
        modifier = modifier
            .clip(shape = ItemShape)
            .background(
                color = mColors.surfaceContainerHigh.copy(alpha = 0.6f),
            ),
    ) {
        content()
    }

@Composable
private fun RecentTrackItemShimmerContent(
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier.padding(all = ItemPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = ItemSpacing),
    ) {
        Box(
            modifier = Modifier
                .size(size = ItemPosterSize)
                .background(
                    color = mColors.surfaceContainerHighest,
                    shape = ItemPosterShape,
                ),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = mDimens.micro6)
                    .background(
                        color = mColors.surfaceContainerHighest,
                        shape = mShapes.small,
                    ),
            )
            Box(
                modifier = Modifier
                    .size(width = 50.dp, height = mDimens.micro4)
                    .background(
                        color = mColors.surfaceContainerHighest,
                        shape = mShapes.small,
                    ),
            )
        }
    }

@Composable
@EchoFlowPreview
private fun RecentTrackItemPreview() =
    Row(horizontalArrangement = Arrangement.spacedBy(mDimens.micro4)) {
        RecentTrackItem(
            isPlaying = false,
            poster = null,
            title = "143 ways to lose yourself",
            artist = null,
        )
        RecentTrackItem(
            isPlaying = true,
            poster = null,
            title = "Over the Horizon",
            artist = "Samsung",
        )
    }
