package com.brbx.design_system.components.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import kotlinx.coroutines.isActive
import org.jetbrains.compose.resources.stringResource
import kotlin.math.PI
import kotlin.math.sin

val LocalCurrentPlayingTrackId = compositionLocalOf<Long?> { null }
val LocalCurrentPlayingTrackPaused = compositionLocalOf<Boolean?> { null }

val currentPlayingTrackId @Composable @ReadOnlyComposable get() = LocalCurrentPlayingTrackId.current
val currentPlayingTrackPaused @Composable @ReadOnlyComposable get() = LocalCurrentPlayingTrackPaused.current

@Immutable
data class TrackItem(
    val id: Long,
    val title: String,
    val poster: String?,
    val artist: String?,
)

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
    trackItem: TrackItem,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    playingTrackId: Long? = currentPlayingTrackId,
    playingTrackPaused: Boolean? = currentPlayingTrackPaused,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    outerCorner: Dp = mShapes.extraLargeRadius,
    innerCorner: Dp = mShapes.smallRadius,
    playingCorner: Dp = PlayingCorner,
) {
    val isPlaying = remember(key1 = playingTrackId) { playingTrackId == trackItem.id }
    TrackItemContainer(
        onClick = { onClick(trackItem.id) },
        isPlaying = isPlaying,
        isFirst = isFirst,
        isLast = isLast,
        outerCorner = outerCorner,
        innerCorner = innerCorner,
        playingCorner = playingCorner,
        modifier = modifier,
    ) {
        TrackItemContent(
            isPaused = playingTrackPaused,
            poster = trackItem.poster,
            title = trackItem.title,
            artist = trackItem.artist ?: stringResource(resource = DesignComponentsRes.string.unknown_artist_label),
            isPlaying = isPlaying,
        )
    }
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
    onClick: () -> Unit,
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
        animationSpec = mMotion.softEffectSpec(),
    )

    val animatedBottomCornerState = animateDpAsState(
        targetValue = targetBottomCorner,
        animationSpec = mMotion.softEffectSpec(),
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
            }
            .clickable(onClick = onClick),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColorState,
        ) { content() }
    }
}

@Composable
private fun colorState(targetValue: Color): State<Color> =
    animateColorAsState(targetValue, animationSpec = mMotion.nonSpatialFastSpec())

@Composable
private fun TrackItemContent(
    isPaused: Boolean?,
    poster: String?,
    title: String,
    artist: String,
    modifier: Modifier = Modifier,
    isPlaying: Boolean?,
) =
    Row(
        modifier = modifier.padding(horizontal = ItemHorizontalPadding, vertical = ItemVerticalPadding),
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
            MarqueeText(
                enabled = isPlaying == true && isPaused == false,
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

        if (isPlaying != null && isPlaying) {
            PlayingEqIcon(
                modifier = Modifier.size(mDimens.micro8),
                color = LocalContentColor.current,
                isPlaying = isPaused?.not() ?: false,
            )
        }
    }

@Composable
private fun PlayingEqIcon(
    modifier: Modifier = Modifier,
    color: Color,
    isPlaying: Boolean = true,
    bars: Int = 3,
    minHeightFraction: Float = 0.28f,
    maxHeightFraction: Float = 1.0f,
    phaseDurationMillis: Int = 2400,
    wanderDurationMillis: Int = 8000,
    gapFraction: Float = 0.30f
) {
    val fullRotation = (2f * PI).toFloat()
    val phaseAnim = remember { Animatable(initialValue = 0f) }
    val wanderAnim = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(key1 = isPlaying, key2 = phaseDurationMillis) {
        if (!isPlaying) return@LaunchedEffect
        while (isActive) {
            val start = (phaseAnim.value % fullRotation).let { if (it < 0f) it + fullRotation else it }
            phaseAnim.snapTo(targetValue = start)
            phaseAnim.animateTo(
                targetValue = start + fullRotation,
                animationSpec = tween(durationMillis = phaseDurationMillis, easing = LinearEasing)
            )
        }
    }

    LaunchedEffect(key1 = isPlaying, key2 = wanderDurationMillis) {
        if (!isPlaying) return@LaunchedEffect
        while (isActive) {
            val start = (wanderAnim.value % fullRotation).let { if (it < 0f) it + fullRotation else it }
            wanderAnim.snapTo(targetValue = start)
            wanderAnim.animateTo(
                targetValue = start + fullRotation,
                animationSpec = tween(durationMillis = wanderDurationMillis, easing = LinearEasing)
            )
        }
    }

    val phase = phaseAnim.value
    val wander = wanderAnim.value

    val activity by animateFloatAsState(
        targetValue = if (isPlaying) 1f else 0f,
        animationSpec = tween(durationMillis = 240, easing = FastOutSlowInEasing),
    )

    val speeds = remember(key1 = bars) { List(size = bars) { (it + 1).toFloat() } }
    val shifts = remember(key1 = bars) { List(size = bars) { i -> i * 0.9f } }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val tentativeBarW = w / (bars + (bars - 1) * (1f + gapFraction))
        val gap = tentativeBarW * gapFraction
        val corner = CornerRadius(x = tentativeBarW / 2f, y = tentativeBarW / 2f)

        repeat(times = bars) { i ->
            val slowShift = 0.6f * sin(x = wander + i * 0.4f)
            val slowAmp   = 0.85f + 0.15f * sin(x = wander * 0.5f + 1.1f + i * 0.3f)

            val v = (sin(x = phase * speeds[i] + shifts[i] + slowShift) * slowAmp + 1f) * 0.5f

            val eased = v * v * (3 - 2 * v)

            val fracBars = minHeightFraction + (maxHeightFraction - minHeightFraction) * eased
            val barH = h * fracBars

            val blendedH = tentativeBarW + (barH - tentativeBarW) * activity

            val top = (h - blendedH) / 2f
            val left = i * (tentativeBarW + gap)

            drawRoundRect(
                color = color,
                topLeft = Offset(x = left, y = top),
                size = Size(width = tentativeBarW, height = blendedH),
                cornerRadius = corner
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
        onClick = {},
        playingTrackId = 1L,
        trackItem = TrackItem(
            id = 1L,
            poster = null,
            title = "143 ways to lose yourself",
            artist = "usedcvnt",
        ),
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
