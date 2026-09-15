package com.brbx.home.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowFilledTonalIconButton
import com.brbx.design_system.components.components.EchoFlowRemoteImage
import com.brbx.design_system.components.components.EllipsedText
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mMotion
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.home.model.HomeIntent
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.broken.Arrows
import dev.chiksmedina.solar.broken.arrows.AltArrowRight
import echoflow.core.design_system.components.generated.resources.DesignComponentsRes
import echoflow.core.design_system.components.generated.resources.unknown_artist_label
import echoflow.feature.home.impl.generated.resources.Res
import echoflow.feature.home.impl.generated.resources.recent_tracks_divider_label
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

private val ItemPadding: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro5
private val ItemSpacing: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro5
private val ItemPosterSize
    @Composable @ReadOnlyComposable get() = mDimens.macro7

private val ItemShape
    @Composable @ReadOnlyComposable get() = mShapes.extraLarge
private val ItemPosterShape: Shape get() = CircleShape

private const val RecentTracksKey = "RecentTracksKey"

internal fun LazyListScope.recentTracks(
    recentLoading: Boolean,
    tracks: ImmutableList<TrackItem>,
    dispatchIntent: (HomeIntent) -> Unit,
) =
    item(key = RecentTracksKey) {
        RecentTracks(
            recentLoading = recentLoading,
            tracks = tracks,
            onFullClick = { dispatchIntent(HomeIntent.Sheets.ToggleRecentSheet) },
            modifier = Modifier
                .animateItem()
                .padding(horizontal = mDimens.micro8),
        )
    }

@Composable
private fun RecentTracks(
    recentLoading: Boolean,
    tracks: ImmutableList<TrackItem>,
    onFullClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    RecentTracksContainerCard(modifier) {
        RecentTracksContent(
            recentLoading = recentLoading,
            tracks = tracks,
            onFullClick = onFullClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }

@Composable
private fun RecentTracksContainerCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) =
    Card(
        shape = mShapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = mColors.surfaceContainerLow,
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = mDimens.micro6),
            content = content,
        )
    }

@Composable
private fun RecentTracksContent(
    recentLoading: Boolean,
    tracks: ImmutableList<TrackItem>,
    onFullClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro5),
    ) {
        RecentTracksHeader(
            recentLoading = recentLoading,
            onFullClick = onFullClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mDimens.micro8),
        )

        RecentTracksGrid(
            recentLoading = recentLoading,
            tracks = tracks,
            modifier = Modifier.fillMaxWidth(),
        )
    }

@Composable
private fun RecentTracksHeader(
    recentLoading: Boolean,
    onFullClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.recent_tracks_divider_label),
            style = mTypography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = mColors.onSurface,
            ),
        )

        EchoFlowFilledTonalIconButton(
            enabled = !recentLoading,
            onClick = onFullClick,
            imageVector = BrokenSolar.Arrows.AltArrowRight,
        )
    }

@Composable
private fun RecentTracksGrid(
    recentLoading: Boolean,
    tracks: ImmutableList<TrackItem>,
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
            val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
            RecentTracksContainer(
                withScroll = false,
                modifier = contentModifier.shimmer(customShimmer = shimmerInstance),
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
            .padding(all = mDimens.micro8),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Nothing here you did not listen music",
            style = mTypography.bodyMedium,
            color = mColors.onSurfaceVariant,
        )
    }

private fun LazyStaggeredGridScope.tracks(tracks: ImmutableList<TrackItem>) =
    itemsIndexed(
        items = tracks,
        key = { _, track -> track.id },
    ) { index, track ->
        RecentTrackItem(
            title = track.title,
            poster = track.poster,
            artist = track.artist,
            modifier = Modifier
                .animateItem()
                .width(calculateTrackContentWidth(key = index)),
        )
    }

private fun LazyStaggeredGridScope.loading() =
    items(count = 10, key = { it }) { index ->
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
) {
    val recentTrackItemHeight = ItemPosterSize + (ItemPadding * 2)
    val recentTracksGridHeight = (recentTrackItemHeight * 3) + (mDimens.micro8 * 2)
    LazyHorizontalStaggeredGrid(
        userScrollEnabled = withScroll,
        rows = StaggeredGridCells.Fixed(count = 3),
        modifier = modifier.height(recentTracksGridHeight),
        contentPadding = PaddingValues(horizontal = mDimens.micro8),
        horizontalItemSpacing = mDimens.micro6,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro6),
        content = content,
    )
}

private fun calculateTrackContentWidth(key: Any): Dp {
    var hash = key.hashCode() xor 0x45d9f3b
    hash = (hash xor (hash ushr 16)) * 0x45d9f3b
    hash = (hash xor (hash ushr 16)) * 0x45d9f3b
    hash = hash xor (hash ushr 16)

    val minWidth = 120
    val maxWidth = 210
    val widthDp = minWidth + (abs(hash) % (maxWidth - minWidth + 1))
    return widthDp.dp
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
    val backgroundColorState by colorState(
        targetValue = if (isPlaying) mColors.primaryContainer else mColors.surfaceContainerHigh,
    )
    val contentColorState by colorState(
        targetValue = if (isPlaying) mColors.onPrimaryContainer else mColors.onSurface,
    )

    Box(
        modifier = modifier
            .clip(shape = ItemShape)
            .background(color = backgroundColorState)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColorState
        ) {
            content()
        }
    }
}

@Composable
private fun colorState(targetValue: Color): State<Color> =
    animateColorAsState(targetValue, animationSpec = mMotion.nonSpatialFastSpec(),)

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
                style = mTypography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = LocalContentColor.current,
                ),
            )
            EllipsedText(
                text = artist,
                style = mTypography.labelMedium.copy(
                    color = LocalContentColor.current.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Normal
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
                color = mColors.surfaceContainerHigh.copy(alpha = 0.75f),
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
