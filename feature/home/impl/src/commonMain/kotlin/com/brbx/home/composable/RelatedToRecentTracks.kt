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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowIcon
import com.brbx.design_system.components.components.EchoFlowRemoteImage
import com.brbx.design_system.components.components.TrackItem
import com.brbx.design_system.components.components.TrackItemShimmer
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import com.brbx.domain.model.Track
import com.brbx.domain.model.User
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import dev.chiksmedina.solar.OutlineSolar
import dev.chiksmedina.solar.outline.ElectronicDevices
import dev.chiksmedina.solar.outline.electronicdevices.TurntableMusicNote
import kotlinx.coroutines.flow.flowOf

private const val RelatedToRecentTracksKey = "RelatedToRecentTracks"

internal fun LazyListScope.relatedToRecentTracks(tracks: LazyPagingItems<Track>?) =
    item(key = RelatedToRecentTracksKey) {
        RelatedToRecentTracks(
            tracks = tracks,
            modifier = Modifier
                .animateItem()
                .padding(horizontal = mDimens.micro8),
        )
    }

@Composable
private fun RelatedToRecentTracks(
    tracks: LazyPagingItems<Track>?,
    modifier: Modifier = Modifier,
    onShowFullPlaylistClick: () -> Unit = {},
) =
    MixContainerCard(modifier = modifier) {
        RelatedToRecentTracksContent(
            tracks = tracks,
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
    tracks: LazyPagingItems<Track>?,
    onShowFullPlaylistClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val count = tracks?.itemCount ?: 0
    val isLoading = tracks == null || tracks.loadState.refresh is LoadState.Loading
    val isEmpty = !isLoading && count == 0
    val itemsToShow = remember(key1 = count) { minOf(a = 4, b = count) }

    val posters = remember(key1 = count, key2 = isLoading) {
        if (tracks == null || count == 0) emptyList()
        else (0 until minOf(a = 3, b = count))
            .mapNotNull { index -> tracks[index]?.highResArtworkUrl }
            .filter { artwork -> artwork.isNotBlank() }
    }

    Column(modifier = modifier) {
        TodayMixHeader(
            posters = posters,
            title = "TODAY MIX",
            subtitle = "Based on history",
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = mDimens.micro6),
        ) {
            when {
                isLoading -> MixShimmerLoading(modifier = Modifier.fillMaxWidth())
                isEmpty -> MixEmptyState(modifier = Modifier.fillMaxWidth())
                else -> MixTracksList(
                    tracks = tracks,
                    itemsToShow = itemsToShow,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        if (!isLoading && !isEmpty) {
            ShowFullPlaylistButton(
                onClick = onShowFullPlaylistClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun TodayMixHeader(
    posters: List<String>,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) =
    TodayMixHeaderContainer(modifier = modifier) {
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
                        mColors.secondary.copy(alpha = 0.5f),
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
    posters: List<String>,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = mDimens.micro1),
    ) {
        Text(
            text = title,
            style = mTypography.titleMedium.copy(
                fontWeight = FontWeight.W800,
                color = mColors.onPrimary,
                letterSpacing = 1.sp,
            ),
        )
        Text(
            text = subtitle,
            style = mTypography.labelMedium.copy(
                color = mColors.onPrimary.copy(alpha = 0.75f),
            ),
        )
    }

    HeaderCollage(posters = posters)
}

@Composable
private fun HeaderCollage(
    posters: List<String>,
    modifier: Modifier = Modifier,
) =
    if (posters.isEmpty()) {
        Spacer(modifier = Modifier.size(size = mDimens.zero))
    } else {
        HeaderCollageContainer(modifier = modifier) {
            HeaderCollageContent(posters = posters)
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
    posters: List<String>,
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
                .background(color = mColors.primaryContainer),
        ) {
            EchoFlowRemoteImage(
                model = posterUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = mColors.primary),
            )
        }
    }

@Composable
private fun MixTracksList(
    tracks: LazyPagingItems<Track>?,
    itemsToShow: Int,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
    ) {
        for (index in 0 until itemsToShow) {
            val track = tracks?.get(index) ?: continue
            TrackItem(
                isPlaying = false,
                poster = track.highResArtworkUrl,
                title = track.title,
                artist = track.user?.name,
                isFirst = index == 0,
                isLast = (index == itemsToShow - 1),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

@Composable
private fun ShowFullPlaylistButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Show full playlist",
) =
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(
            bottomEnd = mShapes.largeRadius,
            bottomStart = mShapes.largeRadius,
            topEnd = mShapes.smallRadius,
            topStart = mShapes.smallRadius,
        ),
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
private fun MixEmptyStateContent() {
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
            imageVector = OutlineSolar.ElectronicDevices.TurntableMusicNote,
            tint = mColors.primary,
            modifier = Modifier.size(size = mDimens.macro3),
        )
    }

    Text(
        text = "Пока нет истории прослушиваний",
        style = mTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = mColors.onSurface,
        textAlign = TextAlign.Center,
    )

    Text(
        text = "Слушайте треки, чтобы сформировать ваш персональный Today Mix",
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
            .height(height = 356.dp)
            .shimmer(customShimmer = shimmerInstance),
        verticalArrangement = Arrangement.spacedBy(space = mDimens.micro2),
    ) {
        repeat(times = 4) { index ->
            TrackItemShimmer(
                isFirst = index == 0,
                isLast = index == 3,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = mDimens.macro6)
                .background(
                    color = mColors.surfaceContainerHighest.copy(alpha = 0.5f),
                    shape = mShapes.large,
                ),
        )
    }
}

private val MockTracks = listOf(
    Track(id = 1L, title = "sleepy f...", description = null, artworkUrl = null, user = User(id = 1L, name = "CODE80", avatarUrl = null)),
    Track(id = 2L, title = "ebanuty melodya", description = null, artworkUrl = null, user = User(id = 2L, name = "Unknown", avatarUrl = null)),
    Track(id = 3L, title = "Over the Horizon", description = null, artworkUrl = null, user = User(id = 3L, name = "Samsung", avatarUrl = null)),
    Track(id = 4L, title = "samsung_galaxy_morning...", description = null, artworkUrl = null, user = User(id = 4L, name = "Samsung", avatarUrl = null)),
)

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksLoadingPreview() =
    RelatedToRecentTracks(
        tracks = null,
        modifier = Modifier.padding(all = mDimens.micro8),
    )

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksEmptyPreview() {
    val tracks = flowOf(PagingData.empty<Track>()).collectAsLazyPagingItems()
    RelatedToRecentTracks(
        tracks = tracks,
        modifier = Modifier.padding(all = mDimens.micro8),
    )
}

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksContentPreview() {
    val tracks = flowOf(PagingData.from(MockTracks)).collectAsLazyPagingItems()
    RelatedToRecentTracks(
        tracks = tracks,
        modifier = Modifier.padding(all = mDimens.micro8),
    )
}

@Composable
@EchoFlowPreview
private fun RelatedToRecentTracksPartialPreview() {
    val tracks = flowOf(PagingData.from(MockTracks.take(n = 2))).collectAsLazyPagingItems()
    RelatedToRecentTracks(
        tracks = tracks,
        modifier = Modifier.padding(all = mDimens.micro8),
    )
}
