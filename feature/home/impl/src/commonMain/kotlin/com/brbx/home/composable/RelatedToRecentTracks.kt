package com.brbx.home.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowIcon
import com.brbx.design_system.components.components.EchoFlowRemoteImage
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.components.components.TrackItemShimmer
import com.brbx.design_system.theme.gFlexFontFamily
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.home.model.HomeIntent
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.broken.ElectronicDevices
import dev.chiksmedina.solar.broken.electronicdevices.TurntableMusicNote
import echoflow.feature.home.impl.generated.resources.Res
import echoflow.feature.home.impl.generated.resources.related_to_recently_full_playlist_button_label
import echoflow.feature.home.impl.generated.resources.related_to_recently_no_recently_description
import echoflow.feature.home.impl.generated.resources.related_to_recently_no_recently_title
import echoflow.feature.home.impl.generated.resources.related_to_recently_subtitle
import echoflow.feature.home.impl.generated.resources.related_to_recently_title
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

private const val RelatedToRecentTracksKey = "RelatedToRecentTracks"

internal fun LazyListScope.relatedToRecentTracks(
    relatedLoading: Boolean,
    tracks: ImmutableList<TrackItem>,
    posters: ImmutableList<String?>,
    dispatchIntent: (HomeIntent) -> Unit,
) =
    item(key = RelatedToRecentTracksKey) {
        RelatedToRecentTracks(
            relatedLoading = relatedLoading,
            tracks = tracks,
            posters = posters,
            dispatchIntent = dispatchIntent,
            modifier = Modifier
                .animateItem()
                .padding(horizontal = mDimens.micro8),
        )
    }

@Composable
internal fun RelatedToRecentTracks(
    relatedLoading: Boolean,
    posters: ImmutableList<String?>,
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
    dispatchIntent: (HomeIntent) -> Unit,
) =
    MixContainerCard(modifier) {
        RelatedToRecentTracksContent(
            relatedLoading = relatedLoading,
            tracks = tracks,
            posters = posters,
            onShowFullPlaylistClick = { dispatchIntent(HomeIntent.Sheets.ToggleMixSheet) },
            modifier = Modifier.fillMaxWidth(),
        )
    }

@Composable
private fun MixContainerCard(
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clipToBounds(),
        ) {
            TracksContainerBottomRightDecoration(
                modifier = Modifier.align(Alignment.BottomEnd),
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content,
            )
        }
    }

@Composable
private fun RelatedToRecentTracksContent(
    relatedLoading: Boolean,
    posters: ImmutableList<String?>,
    tracks: ImmutableList<TrackItem>,
    onShowFullPlaylistClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro6)
    ) {
        TodayMixHeader(
            posters = posters,
            title = stringResource(Res.string.related_to_recently_title),
            subtitle = stringResource(Res.string.related_to_recently_subtitle),
            modifier = Modifier.fillMaxWidth(),
        )

        val micro6 = mDimens.micro6
        val modifier = remember {
            Modifier
                .fillMaxWidth()
                .padding(horizontal = micro6)
        }
        TracksCrossfade(
            loading = relatedLoading,
            tracks = tracks,
            loadingContent = { MixShimmerLoading(modifier) },
            emptyContent = { MixEmptyState(modifier) },
            listContent = { tracks -> MixTracksList(tracks, modifier) }
        )

        if (relatedLoading || tracks.isNotEmpty()) {
            ShowFullPlaylistButton(
                enabled = !relatedLoading,
                onClick = onShowFullPlaylistClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = mDimens.micro6),
            )

            Spacer(Modifier)
        }
    }

@Composable
private fun TodayMixHeader(
    posters: ImmutableList<String?>,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) =
    TodayMixHeaderContainer(modifier) {
        TodayMixHeaderContent(
            posters = posters,
            title = title,
            subtitle = subtitle,
        )
    }

@Composable
private fun TodayMixHeaderContainer(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) =
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        mColors.primaryContainer,
                        mColors.tertiaryContainer,
                    ),
                ),
            ),
    ) {
        TodayMixBackgroundDecoration(
            modifier = Modifier.align(Alignment.TopEnd),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mDimens.micro8, vertical = mDimens.micro7),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }

@Composable
private fun AsymmetricDecoration(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    accentColor: Color = Color.Unspecified,
    shapePath: Path.(size: Size) -> Unit,
    accentDraw: (DrawScope.(size: Size) -> Unit)? = null,
) =
    Canvas(modifier = modifier) {
        val path = Path().apply { shapePath(size) }
        drawPath(path = path, color = primaryColor)

        if (accentColor != Color.Unspecified && accentDraw != null) {
            accentDraw(size)
        }
    }

@Composable
private fun TodayMixBackgroundDecoration(
    modifier: Modifier = Modifier,
    shapeColor: Color = mColors.onPrimaryContainer.copy(alpha = 0.08f),
    accentDotColor: Color = mColors.onPrimaryContainer.copy(alpha = 0.12f),
) =
    AsymmetricDecoration(
        modifier = modifier
            .size(width = 110.dp, height = 85.dp)
            .offset(x = 20.dp, y = (-16).dp),
        primaryColor = shapeColor,
        accentColor = accentDotColor,
        shapePath = { size ->
            val w = size.width
            val h = size.height
            moveTo(w * 0.35f, 0f)
            cubicTo(w * 0.85f, -h * 0.15f, w * 1.15f, h * 0.45f, w * 0.80f, h * 0.85f)
            cubicTo(w * 0.55f, h * 1.15f, w * 0.10f, h * 0.90f, 0f, h * 0.55f)
            cubicTo(-w * 0.08f, h * 0.20f, w * 0.05f, h * 0.05f, w * 0.35f, 0f)
            close()
        },
        accentDraw = { size ->
            drawCircle(
                color = accentDotColor,
                radius = 4.dp.toPx(),
                center = Offset(size.width * 0.18f, size.height * 0.78f),
            )
        },
    )

@Composable
private fun TracksContainerBottomRightDecoration(
    modifier: Modifier = Modifier,
    primaryColor: Color = mColors.primary.copy(alpha = 0.05f),
    accentColor: Color = mColors.tertiary.copy(alpha = 0.07f),
) =
    AsymmetricDecoration(
        modifier = modifier
            .size(width = 95.dp, height = 80.dp)
            .offset(x = 24.dp, y = 20.dp),
        primaryColor = primaryColor,
        accentColor = accentColor,
        shapePath = { size ->
            val w = size.width
            val h = size.height
            moveTo(w * 0.40f, h * 0.05f)
            cubicTo(w * 0.95f, -h * 0.15f, w * 1.25f, h * 0.45f, w * 0.90f, h * 0.95f)
            cubicTo(w * 0.65f, h * 1.15f, w * 0.35f, h * 0.80f, w * 0.15f, h * 0.90f)
            cubicTo(-w * 0.10f, h * 0.70f, w * 0.05f, h * 0.30f, w * 0.40f, h * 0.05f)
            close()
        },
        accentDraw = { size ->
            val w = size.width
            val h = size.height
            val accentPath = Path().apply {
                moveTo(w * 0.12f, h * 0.25f)
                cubicTo(w * 0.32f, h * 0.12f, w * 0.38f, h * 0.42f, w * 0.18f, h * 0.48f)
                cubicTo(-w * 0.02f, h * 0.52f, -w * 0.02f, h * 0.32f, w * 0.12f, h * 0.25f)
                close()
            }
            drawPath(path = accentPath, color = accentColor)
        },
    )

@Composable
private fun TodayMixHeaderContent(
    posters: ImmutableList<String?>,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = title,
            style = rememberTodayMixTitleStyle(),
        )
        Text(
            text = subtitle,
            style = mTypography.bodyMedium.copy(
                color = mColors.onPrimaryContainer.copy(alpha = 0.8f),
                fontWeight = FontWeight.W600,
            ),
        )
    }

    HeaderCollage(posters)
}

@Composable
private fun rememberTodayMixTitleStyle(): TextStyle {
    val gFlex = gFlexFontFamily(
        FontVariation.width(value = 136f),
        FontVariation.grade(value = 40),
        FontVariation.Setting(name = "XTRA", value = 520f),
        FontVariation.Setting(name = "YOPQ", value = 90f),
        FontVariation.Setting(name = "YTLC", value = 505f),
    )
    val baseStyle = mTypography.titleLarge
    val color = mColors.onPrimaryContainer
    return remember(key1 = baseStyle, key2 = color, key3 = gFlex) {
        baseStyle.copy(
            fontFamily = gFlex,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.25).sp,
        )
    }
}

@Composable
private fun HeaderCollage(
    posters: ImmutableList<String?>,
    modifier: Modifier = Modifier,
) =
    if (posters.isEmpty()) {
        Spacer(modifier = Modifier.size(size = mDimens.zero))
    } else {
        HeaderCollageContainer(modifier) {
            HeaderCollageContent(posters)
        }
    }

@Composable
private fun HeaderCollageContainer(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) =
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = -mDimens.micro5),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )

@Composable
private fun HeaderCollageContent(
    posters: ImmutableList<String?>,
) =
    posters.forEach { posterUrl ->
        Box(
            modifier = Modifier
                .size(size = mDimens.macro5)
                .border(
                    width = 2.dp,
                    color = mColors.tertiary,
                    shape = CircleShape,
                )
                .clip(shape = CircleShape)
                .background(color = mColors.secondary)
        ) {
            EchoFlowRemoteImage(
                model = posterUrl,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

@Composable
private fun MixTracksList(
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
    ) {
        tracks.forEachIndexed { index, track ->
            TrackItem(
                trackItem = track,
                isPlaying = false,
                isFirst = index == 0,
                isLast = (index == tracks.lastIndex),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

@Composable
private fun ShowFullPlaylistButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = stringResource(Res.string.related_to_recently_full_playlist_button_label),
) =
    TextButton(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.W600,
            ),
        )
    }

@Composable
private fun MixEmptyState(
    modifier: Modifier = Modifier,
) =
    MixEmptyStateContainer(modifier = modifier) {
        MixEmptyStateContent()
    }

@Composable
private fun MixEmptyStateContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) =
    Box(
        modifier = modifier
            .height(height = 356.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = mDimens.macro2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = mDimens.micro4),
            content = content,
        )
    }

@Composable
private fun MixEmptyStateContent(
    title: String = stringResource(Res.string.related_to_recently_no_recently_title),
    description: String = stringResource(Res.string.related_to_recently_no_recently_description),
) {
    Box(
        modifier = Modifier
            .size(size = 56.dp)
            .background(
                color = mColors.primaryContainer,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        EchoFlowIcon(
            imageVector = BrokenSolar.ElectronicDevices.TurntableMusicNote,
            tint = mColors.onPrimaryContainer,
            modifier = Modifier.size(size = mDimens.macro3),
        )
    }

    Text(
        text = title,
        style = mTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        color = mColors.onSurface,
        textAlign = TextAlign.Center,
    )

    Text(
        text = description,
        style = mTypography.bodyMedium,
        color = mColors.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun MixShimmerLoading(
    modifier: Modifier = Modifier,
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    Column(
        modifier = modifier
            .shimmer(customShimmer = shimmerInstance),
        verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
    ) {
        repeat(times = RelatedToRecentlySnapshotCount) { index ->
            TrackItemShimmer(
                isFirst = index == 0,
                isLast = index == RelatedToRecentlySnapshotCount - 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private val PreviewTracks = persistentListOf(
    TrackItem(
        id = 1L,
        title = "Midnight City",
        poster = "https://example.com/artwork1.jpg",
        artist = "M83",
    ),
    TrackItem(
        id = 2L,
        title = "Starboy",
        poster = "https://example.com/artwork2.jpg",
        artist = "The Weeknd",
    ),
    TrackItem(
        id = 3L,
        title = "Get Lucky",
        poster = "https://example.com/artwork3.jpg",
        artist = "Daft Punk",
    ),
    TrackItem(
        id = 4L,
        title = "Resonance",
        poster = "https://example.com/artwork4.jpg",
        artist = "HOME",
    ),
)

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksContentWithPostersPreview() =
    RelatedToRecentTracks(
        relatedLoading = false,
        tracks = PreviewTracks,
        posters = persistentListOf(),
        modifier = Modifier.padding(all = mDimens.micro8),
        dispatchIntent = {}
    )

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksLoadingPreview() =
    RelatedToRecentTracks(
        relatedLoading = true,
        tracks = persistentListOf(),
        posters = persistentListOf(),
        modifier = Modifier.padding(all = mDimens.micro8),
        dispatchIntent = {}
    )

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksEmptyPreview() =
    RelatedToRecentTracks(
        relatedLoading = false,
        tracks = persistentListOf(),
        posters = persistentListOf(),
        modifier = Modifier.padding(all = mDimens.micro8),
        dispatchIntent = {}
    )
