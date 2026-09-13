package com.brbx.design_system.components.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mMotion
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import echoflow.core.design_system.components.generated.resources.DesignComponentsRes
import echoflow.core.design_system.components.generated.resources.unknown_artist_label
import org.jetbrains.compose.resources.stringResource

private val PlayingCorner = 100.dp
private val ItemHorizontalPadding: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro6
private val ItemVerticalPadding: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro5
private val ItemSpacing: Dp
    @Composable @ReadOnlyComposable get() = mDimens.micro6
private val ItemPosterSize: Dp get() = 52.dp

private val ItemPosterShape: Shape
    @Composable @ReadOnlyComposable get() = mShapes.medium

@Composable
fun TrackItem(
    isPlaying: Boolean,
    poster: String?,
    title: String,
    artist: String?,
    modifier: Modifier = Modifier,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    outerCorner: Dp = mShapes.extraLargeRadius,
    innerCorner: Dp = mShapes.extraSmallRadius,
    playingCorner: Dp = PlayingCorner,
) =
    TrackItemContainer(
        isPlaying = isPlaying,
        isFirst = isFirst,
        isLast = isLast,
        outerCorner = outerCorner,
        innerCorner = innerCorner,
        playingCorner = playingCorner,
        modifier = modifier,
    ) {
        TrackItemContent(
            isPlaying = isPlaying,
            poster = poster,
            title = title,
            artist = artist ?: stringResource(resource = DesignComponentsRes.string.unknown_artist_label),
        )
    }

@Composable
fun TrackItemShimmer(
    modifier: Modifier = Modifier,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    outerCorner: Dp = mShapes.extraLargeRadius,
    innerCorner: Dp = mShapes.extraSmallRadius,
) =
    TrackItemShimmerContainer(
        isFirst = isFirst,
        isLast = isLast,
        outerCorner = outerCorner,
        innerCorner = innerCorner,
        modifier = modifier,
    ) {
        TrackItemShimmerContent()
    }

@Composable
private fun TrackItemContainer(
    isPlaying: Boolean,
    isFirst: Boolean,
    isLast: Boolean,
    outerCorner: Dp,
    innerCorner: Dp,
    playingCorner: Dp,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val backgroundColorState by colorState(
        targetValue = if (isPlaying) mColors.primaryContainer else mColors.surfaceContainerHigh
    )
    val contentColorState by colorState(
        targetValue = if (isPlaying) mColors.onPrimaryContainer else mColors.onSurface
    )

    val targetTopCorner = when {
        isPlaying -> playingCorner
        isFirst -> outerCorner
        else -> innerCorner
    }

    val targetBottomCorner = when {
        isPlaying -> playingCorner
        isLast -> outerCorner
        else -> innerCorner
    }

    val animatedTopCornerState = animateDpAsState(
        targetValue = targetTopCorner,
        animationSpec = mMotion.mediumSpatialSpec(),
    )

    val animatedBottomCornerState = animateDpAsState(
        targetValue = targetBottomCorner,
        animationSpec = mMotion.mediumSpatialSpec(),
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                val top = animatedTopCornerState.value
                val bottom = animatedBottomCornerState.value
                shape = RoundedCornerShape(
                    topStart = top,
                    topEnd = top,
                    bottomEnd = bottom,
                    bottomStart = bottom,
                )
                clip = true
            }
            .drawBehind {
                drawRect(color = backgroundColorState)
            },
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColorState
        ) { content() }
    }
}

@Composable
private fun colorState(targetValue: Color): State<Color> =
    animateColorAsState(targetValue, animationSpec = mMotion.nonSpatialFastSpec())

@Composable
private fun TrackItemContent(
    isPlaying: Boolean,
    poster: String?,
    title: String,
    artist: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = ItemHorizontalPadding, vertical = ItemVerticalPadding),
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
            modifier = Modifier.weight(weight = 1f),
            verticalArrangement = Arrangement.Center,
        ) {
            EllipsedText(
                text = title,
                style = mTypography.bodyLarge.copy(
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
}

@Composable
private fun TrackItemShimmerContainer(
    isFirst: Boolean,
    isLast: Boolean,
    outerCorner: Dp,
    innerCorner: Dp,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val topCorner = if (isFirst) outerCorner else innerCorner
    val bottomCorner = if (isLast) outerCorner else innerCorner

    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = topCorner,
                    topEnd = topCorner,
                    bottomEnd = bottomCorner,
                    bottomStart = bottomCorner,
                ),
            )
            .background(
                color = mColors.surfaceContainerHigh.copy(alpha = 0.75f),
            ),
        content = content,
    )
}

@Composable
private fun TrackItemShimmerContent(
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier
            .padding(horizontal = ItemHorizontalPadding, vertical = ItemVerticalPadding),
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
            modifier = Modifier.weight(weight = 1f),
            verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.6f)
                    .height(height = mDimens.micro7)
                    .background(
                        color = mColors.surfaceContainerHighest,
                        shape = mShapes.small,
                    ),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.35f)
                    .height(height = mDimens.micro5)
                    .background(
                        color = mColors.surfaceContainerHighest,
                        shape = mShapes.small,
                    ),
            )
        }
    }

@Composable
@EchoFlowPreview
private fun TrackItemPreview() =
    TrackItem(
        isPlaying = false,
        poster = null,
        title = "143 ways to lose yourself",
        artist = "usedcvnt",
        isFirst = true,
    )

@Composable
@EchoFlowPreview
private fun TrackItemShimmerPreview() =
    TrackItemShimmer(
        isFirst = true,
        isLast = false,
        modifier = Modifier.fillMaxWidth(),
    )
