package com.brbx.design_system.components.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
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
import echoflow.core.design_system.components.generated.resources.Res
import echoflow.core.design_system.components.generated.resources.unknown_artist_label
import org.jetbrains.compose.resources.stringResource

private val PlayingCorner = 100.dp

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
    innerCorner: Dp = mShapes.largeRadius,
    playingCorner: Dp = PlayingCorner,
) =
    TrackItemContainer(
        isPlaying = isPlaying,
        isFirst = isFirst,
        isLast = isLast,
        outerCorner = outerCorner,
        innerCorner = innerCorner,
        playingCorner = playingCorner,
        modifier = modifier
    ) {
        TrackItemContent(
            isPlaying = isPlaying,
            poster = poster,
            title = title,
            artist = artist ?: stringResource(Res.string.unknown_artist_label)
        )
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
    content: @Composable BoxScope.() -> Unit
) {
    val backgroundColorState = animateColorAsState(
        targetValue = if (isPlaying) mColors.primary else mColors.surfaceContainerHigh,
        animationSpec = mMotion.nonSpatialFastSpec(),
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
                drawRect(color = backgroundColorState.value)
            },
        content = content
    )
}

@Composable
private fun TrackItemContent(
    isPlaying: Boolean,
    poster: String?,
    title: String,
    artist: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = mDimens.micro8, vertical = mDimens.micro6),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(mDimens.micro6),
    ) {
        EchoFlowRemoteImage(
            model = poster,
            modifier = Modifier
                .size(mDimens.macro8)
                .clip(shape = mShapes.large)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            val textColorState by animateColorAsState(
                targetValue = if (isPlaying) mColors.onPrimary else mColors.onSurface,
                animationSpec = mMotion.nonSpatialFastSpec(),
            )
            TextWithEllipsis(
                text = title,
                style = mTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W600,
                    color = textColorState,
                )
            )
            TextWithEllipsis(
                text = artist,
                style = mTypography.labelMedium.copy(
                    color = textColorState.copy(alpha = 0.7f)
                )
            )
        }
    }
}

@Composable
@EchoFlowPreview
private fun TrackItemPreview() =
    TrackItem(
        isPlaying = true,
        poster = null,
        title = "143 ways to lose yourself",
        artist = "usedcvnt",
        isFirst = true
    )