package com.brbx.design_system.components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.gFlexFontFamily
import com.brbx.design_system.theme.mColors
import com.brbx.design_system.theme.mDimens
import com.brbx.design_system.theme.mShapes
import com.brbx.design_system.theme.mTypography
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.bold.VideoAudioSound
import dev.chiksmedina.solar.bold.videoaudiosound.Play
import dev.chiksmedina.solar.bold.videoaudiosound.Shuffle
import echoflow.core.design_system.components.generated.resources.DesignComponentsRes
import echoflow.core.design_system.components.generated.resources.playlist_sheet_mix_button_label
import echoflow.core.design_system.components.generated.resources.playlist_sheet_play_button_label
import echoflow.core.design_system.components.generated.resources.playlist_sheet_tracks_count
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

private val PillCornerSize = 100.dp
private const val HeaderKey = "HeaderKey"
private const val ButtonsKey = "ButtonsKey"
private const val ParallaxScrollMultiplier = 0.8f

@Composable
fun PlaylistSheet(
    onDismissRequest: () -> Unit,
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    onPlayClick: () -> Unit = {},
    onMixClick: () -> Unit = {},
) {
    if (!visible) return

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    EchoFlowSheet(
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        SheetContent(
            playlistName = playlistName,
            tracks = tracks,
            onPlayClick = onPlayClick,
            onMixClick = onMixClick,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        )
    }
}

@Composable
private fun SheetContent(
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit,
    onMixClick: () -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro2),
        contentPadding = PaddingValues(vertical = mDimens.micro8),
    ) {
        playlistSheetContent(
            listState = listState,
            playlistName = playlistName,
            tracks = tracks,
            onPlayClick = onPlayClick,
            onMixClick = onMixClick,
        )
    }
}

private fun LazyListScope.playlistSheetContent(
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
    listState: LazyListState,
    onPlayClick: () -> Unit,
    onMixClick: () -> Unit,
) {
    item(key = HeaderKey) {
        val posters = remember(tracks) {
            val first = tracks.getOrNull(0)?.poster
            val second = tracks.getOrNull(1)?.poster ?: first
            val third = tracks.getOrNull(2)?.poster ?: second
            Triple(first, second, third)
        }

        Header(
            firstPoster = posters.first,
            secondPoster = posters.second,
            thirdPoster = posters.third,
            playlistName = playlistName,
            tracksCount = tracks.size,
            modifier = Modifier
                .animateItem()
                .fillMaxWidth()
                .headerParallaxScroll(listState)
                .padding(bottom = mDimens.macro3)
        )
    }

    item(key = ButtonsKey) {
        HeaderButtons(
            onPlayClick = onPlayClick,
            onMixClick = onMixClick,
            modifier = Modifier
                .animateItem()
                .fillMaxWidth()
                .padding(
                    bottom = mDimens.micro4,
                    start = mDimens.micro8,
                    end = mDimens.micro8
                ),
        )
    }

    itemsIndexed(
        items = tracks,
        key = { _, item -> item.id },
    ) { index, track ->
        TrackItem(
            isPlaying = false,
            trackItem = track,
            isFirst = index == 0,
            isLast = index == tracks.lastIndex,
            modifier = Modifier
                .animateItem()
                .fillMaxWidth()
                .padding(horizontal = mDimens.micro8),
        )
    }
}

@Composable
private fun Header(
    firstPoster: String?,
    secondPoster: String?,
    thirdPoster: String?,
    playlistName: String,
    tracksCount: Int,
    modifier: Modifier = Modifier,
) =
    Column(modifier) {
        HeaderCollage(
            firstPoster = firstPoster,
            secondPoster = secondPoster,
            thirdPoster = thirdPoster,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = mDimens.micro8)
        )

        HeaderTitle(
            modifier = Modifier.padding(horizontal = mDimens.micro8),
            title = playlistName,
            tracksCount = tracksCount
        )
    }

@Composable
private fun HeaderCollage(
    firstPoster: String?,
    secondPoster: String ?,
    thirdPoster: String?,
    modifier: Modifier = Modifier,
) =
    HeaderCollageContainer(modifier) {
        HeaderCollageContent(firstPoster, secondPoster, thirdPoster)
    }

@Composable
private fun HeaderCollageContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) =
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth(),
    ) {
        content()

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(3f)
                .fillMaxWidth()
                .height(90.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            mColors.surfaceContainerLow.copy(alpha = 0f),
                            mColors.surfaceContainerLow,
                        )
                    )
                )
        )
    }

@Composable
private fun HeaderCollageContent(
    firstPoster: String?,
    secondPoster: String?,
    thirdPoster: String?,
) {
    EchoFlowRemoteImage(
        model = firstPoster,
        modifier = Modifier
            .offset(x = (-60).dp, y = 15.dp)
            .size(120.dp)
            .rotate(degrees = -7f)
            .clip(shape = mShapes.extraLarge)
            .background(color = mColors.primary)
            .zIndex(1f)
    )

    EchoFlowRemoteImage(
        model = thirdPoster,
        modifier = Modifier
            .offset(x = 70.dp, y = (-20).dp)
            .size(110.dp)
            .rotate(degrees = 15f)
            .clip(shape = CircleShape)
            .background(color = mColors.tertiaryContainer)
            .zIndex(0f)
    )

    EchoFlowRemoteImage(
        model = secondPoster,
        modifier = Modifier
            .offset(x = 5.dp, y = 5.dp)
            .size(135.dp)
            .rotate(degrees = 4f)
            .background(
                color = mColors.surfaceContainerLow,
                shape = mShapes.extraLarge,
            )
            .padding(all = 4.dp)
            .clip(shape = RoundedCornerShape(mShapes.extraLargeRadius - 4.dp))
            .background(color = mColors.secondary)
            .zIndex(2f)
    )
}

@Composable
private fun HeaderTitle(
    title: String,
    tracksCount: Int,
    modifier: Modifier = Modifier,
) =
    Column(modifier = modifier) {
        EllipsedText(
            text = title,
            style = mTypography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = gFlexFontFamily(),
                color = mColors.onSurface
            ),
        )

        EllipsedText(
            text = pluralStringResource(
                resource = DesignComponentsRes.plurals.playlist_sheet_tracks_count,
                quantity = tracksCount,
                tracksCount,
            ),
            style = mTypography.bodyMedium.copy(
                color = mColors.onSurfaceVariant
            ),
        )
    }

@Composable
private fun HeaderButtons(
    onPlayClick: () -> Unit,
    onMixClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(mDimens.micro4),
    ) {
        PlaylistButton(onClick = onPlayClick)

        PlaylistButton(
            onClick = onMixClick,
            text = stringResource(DesignComponentsRes.string.playlist_sheet_mix_button_label),
            icon = BoldSolar.VideoAudioSound.Shuffle,
            colors = ButtonDefaults.buttonColors(
                contentColor = mColors.onSecondary,
                containerColor = mColors.secondary
            ),
            shape = RoundedCornerShape(
                topEnd = PillCornerSize,
                bottomEnd = PillCornerSize,
                topStart = mShapes.smallRadius,
                bottomStart = mShapes.smallRadius,
            )
        )
    }

@Composable
private fun RowScope.PlaylistButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = BoldSolar.VideoAudioSound.Play,
    text: String = stringResource(DesignComponentsRes.string.playlist_sheet_play_button_label),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = mColors.onPrimary,
        containerColor = mColors.primary,
    ),
    shape: Shape = RoundedCornerShape(
        topStart = PillCornerSize,
        bottomStart = PillCornerSize,
        topEnd = mShapes.smallRadius,
        bottomEnd = mShapes.smallRadius,
    )
) =
    ButtonWithIcon(
        onClick = onClick,
        icon = icon,
        text = text,
        iconSize = 18.dp,
        colors = colors,
        shape = shape,
        modifier = modifier
            .weight(1f)
            .height(58.dp),
    )

private fun Modifier.headerParallaxScroll(listState: LazyListState) = graphicsLayer {
    if (listState.firstVisibleItemIndex == 0) {
        val headerItemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == 0 }
        if (headerItemInfo != null) {
            val scrollOffset = listState.firstVisibleItemScrollOffset.toFloat()
            val scrollRatio = scrollOffset / headerItemInfo.size.toFloat()

            alpha = (1f - scrollRatio).coerceIn(0f, 1f)
            translationY = scrollOffset * ParallaxScrollMultiplier
        }
    } else {
        alpha = 0f
    }
}

// ======================== PREVIEWS ========================

private val PreviewTracks = persistentListOf(
    TrackItem(
        id = 1L,
        title = "143 ways to lose yourself",
        poster = null,
        artist = "usedcvnt",
    ),
    TrackItem(
        id = 2L,
        title = "Midnight City",
        poster = null,
        artist = "M83",
    ),
    TrackItem(
        id = 3L,
        title = "Starboy",
        poster = null,
        artist = "The Weeknd",
    ),
)

@Composable
@EchoFlowPreview
private fun PlaylistSheetContentPreview() {
    SheetContent(
        playlistName = "Today's Mix",
        tracks = PreviewTracks,
        onPlayClick = {},
        onMixClick = {},
    )
}

@Composable
@EchoFlowPreview
private fun PlaylistSheetContentEmptyPreview() {
    SheetContent(
        playlistName = "Empty Playlist",
        tracks = persistentListOf(),
        onPlayClick = {},
        onMixClick = {},
    )
}
