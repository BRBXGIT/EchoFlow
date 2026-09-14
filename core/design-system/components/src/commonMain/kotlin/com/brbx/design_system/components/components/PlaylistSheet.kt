package com.brbx.design_system.components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
private const val ButtonKey = "ButtonsKey"
private const val PlaylistNameKey = "PlaylistNameKey"

@Composable
fun PlaylistSheet(
    onDismissRequest: () -> Unit,
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    playingTrackId: Long? = null,
    onPlayClick: () -> Unit = {},
    onMixClick: () -> Unit = {},
    onTrackClick: ((TrackItem) -> Unit)? = null,
) {
    if (!visible) return

    EchoFlowSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        PlaylistSheetContent(
            playlistName = playlistName,
            tracks = tracks,
            playingTrackId = playingTrackId,
            onPlayClick = onPlayClick,
            onMixClick = onMixClick,
            onTrackClick = onTrackClick,
        )
    }
}

@Composable
private fun PlaylistSheetContent(
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
    modifier: Modifier = Modifier,
    playingTrackId: Long? = null,
    onPlayClick: () -> Unit = {},
    onMixClick: () -> Unit = {},
    onTrackClick: ((TrackItem) -> Unit)? = null,
) =
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro2),
        contentPadding = PaddingValues(all = mDimens.micro8),
    ) {
        item(PlaylistNameKey) { Spacer(Modifier.height(mDimens.macro8)) }

        item(HeaderKey) {
            Header(
                title = playlistName,
                tracksCount = tracks.size,
                onPlayClick = onPlayClick,
                onMixClick = onMixClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
            )
        }

        item(ButtonKey) { Spacer(Modifier.height(mDimens.micro1)) }

        itemsIndexed(
            items = tracks,
            key = { _, item -> item.id },
        ) { index, track ->
            val trackModifier = if (onTrackClick != null) {
                Modifier
                    .fillMaxWidth()
                    .clickable { onTrackClick(track) }
            } else {
                Modifier.fillMaxWidth()
            }

            TrackItem(
                isPlaying = track.id == playingTrackId,
                trackItem = track,
                modifier = trackModifier,
                isFirst = index == 0,
                isLast = index == tracks.lastIndex,
            )
        }
    }

@Composable
private fun Header(
    title: String,
    tracksCount: Int,
    onPlayClick: () -> Unit,
    onMixClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.macro1),
    ) {
        HeaderTitle(
            title = title,
            tracksCount = tracksCount,
        )

        HeaderButtons(
            onPlayClick = onPlayClick,
            onMixClick = onMixClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }

@Composable
private fun HeaderTitle(
    title: String,
    tracksCount: Int,
    modifier: Modifier = Modifier,
) =
    Column(modifier) {
        Text(
            text = title,
            style = mTypography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = gFlexFontFamily(),
            ),
            color = mColors.onSurface,
        )

        Text(
            text = pluralStringResource(
                resource = DesignComponentsRes.plurals.playlist_sheet_tracks_count,
                quantity = tracksCount,
                tracksCount,
            ),
            style = mTypography.bodyMedium,
            color = mColors.onSurfaceVariant,
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
        PlaylistButton(
            onClick = onPlayClick,
            icon = BoldSolar.VideoAudioSound.Play,
            text = stringResource(DesignComponentsRes.string.playlist_sheet_play_button_label),
            colors = ButtonDefaults.buttonColors(
                contentColor = mColors.onPrimary,
                containerColor = mColors.primary,
            ),
            shape = RoundedCornerShape(
                topStart = PillCornerSize,
                bottomStart = PillCornerSize,
                topEnd = mShapes.smallRadius,
                bottomEnd = mShapes.smallRadius,
            ),
        )

        PlaylistButton(
            onClick = onMixClick,
            text = stringResource(DesignComponentsRes.string.playlist_sheet_mix_button_label),
            icon = BoldSolar.VideoAudioSound.Shuffle,
        )
    }

@Composable
private fun RowScope.PlaylistButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = mColors.onSecondary,
        containerColor = mColors.secondary,
    ),
    shape: Shape = RoundedCornerShape(
        topStart = mShapes.smallRadius,
        bottomStart = mShapes.smallRadius,
        topEnd = PillCornerSize,
        bottomEnd = PillCornerSize,
    )
) =
    ButtonWithIcon(
        onClick = onClick,
        icon = icon,
        text = text,
        iconSize = 18.dp,
        colors = colors,
        shape = shape,
        modifier = Modifier
            .weight(1f)
            .height(mDimens.macro8),
    )

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
private fun PlaylistSheetContentPreview() =
    PlaylistSheetContent(
        playlistName = "Today's Mix",
        tracks = PreviewTracks,
        playingTrackId = 2L,
    )

@Composable
@EchoFlowPreview
private fun PlaylistSheetContentEmptyPreview() =
    PlaylistSheetContent(
        playlistName = "Empty Playlist",
        tracks = persistentListOf(),
    )
