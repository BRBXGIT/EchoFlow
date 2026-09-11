package com.brbx.home.composable

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
import androidx.compose.ui.graphics.Brush
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
import com.brbx.domain.model.common.Track
import com.brbx.domain.model.common.User
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
    tracks: ImmutableList<Track>,
    posters: ImmutableList<String?>,
) =
    item(key = RelatedToRecentTracksKey) {
        RelatedToRecentTracks(
            relatedLoading = relatedLoading,
            tracks = tracks,
            posters = posters,
            modifier = Modifier
                .animateItem()
                .padding(horizontal = mDimens.micro8),
        )
    }

@Composable
private fun RelatedToRecentTracks(
    relatedLoading: Boolean,
    posters: ImmutableList<String?>,
    tracks: ImmutableList<Track>,
    modifier: Modifier = Modifier,
    onShowFullPlaylistClick: () -> Unit = {},
) =
    MixContainerCard(modifier) {
        RelatedToRecentTracksContent(
            relatedLoading = relatedLoading,
            tracks = tracks,
            posters = posters,
            onShowFullPlaylistClick = onShowFullPlaylistClick,
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }

@Composable
private fun RelatedToRecentTracksContent(
    relatedLoading: Boolean,
    posters: ImmutableList<String?>,
    tracks: ImmutableList<Track>,
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
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        mColors.primary,
                        mColors.tertiary,
                    ),
                ),
            )
            .padding(horizontal = mDimens.micro8, vertical = mDimens.micro7),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }

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
            style = mTypography.labelLarge.copy(
                color = mColors.onPrimary.copy(alpha = 0.75f),
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
    val color = mColors.onPrimary
    return remember(key1 = baseStyle, key2 = color, key3 = gFlex) {
        baseStyle.copy(
            fontFamily = gFlex,
            color = color,
            fontWeight = FontWeight.W800,
            fontSize = 20.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.35).sp,
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
                    width = 1.dp,
                    color = mColors.onSecondary,
                    shape = CircleShape,
                )
                .clip(shape = CircleShape)
                .background(color = mColors.onTertiaryContainer)
        ) {
            EchoFlowRemoteImage(
                model = posterUrl,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

@Composable
private fun MixTracksList(
    tracks: ImmutableList<Track>,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
    ) {
        tracks.forEachIndexed { index, track ->
            TrackItem(
                isPlaying = false,
                poster = track.highResArtworkUrl,
                title = track.title,
                artist = track.user?.name,
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
                color = mColors.surfaceContainerHigh,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        EchoFlowIcon(
            imageVector = BrokenSolar.ElectronicDevices.TurntableMusicNote,
            tint = mColors.primary,
            modifier = Modifier.size(size = mDimens.macro3),
        )
    }

    Text(
        text = title,
        style = mTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = mColors.onSurface,
        textAlign = TextAlign.Center,
    )

    Text(
        text = description,
        style = mTypography.bodySmall,
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
    Track(
        id = 1L,
        title = "Midnight City",
        description = null,
        artworkUrl = "https://example.com/artwork1.jpg",
        user = User(id = 1L, name = "M83", avatarUrl = null),
    ),
    Track(
        id = 2L,
        title = "Starboy",
        description = null,
        artworkUrl = "https://example.com/artwork2.jpg",
        user = User(id = 2L, name = "The Weeknd", avatarUrl = null),
    ),
    Track(
        id = 3L,
        title = "Get Lucky",
        description = null,
        artworkUrl = "https://example.com/artwork3.jpg",
        user = User(id = 3L, name = "Daft Punk", avatarUrl = null),
    ),
    Track(
        id = 4L,
        title = "Resonance",
        description = null,
        artworkUrl = "https://example.com/artwork4.jpg",
        user = User(id = 4L, name = "HOME", avatarUrl = null),
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
    )

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksLoadingPreview() =
    RelatedToRecentTracks(
        relatedLoading = true,
        tracks = persistentListOf(),
        posters = persistentListOf(),
        modifier = Modifier.padding(all = mDimens.micro8),
    )

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksEmptyPreview() =
    RelatedToRecentTracks(
        relatedLoading = false,
        tracks = persistentListOf(),
        posters = persistentListOf(),
        modifier = Modifier.padding(all = mDimens.micro8),
    )
