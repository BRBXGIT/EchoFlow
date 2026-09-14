package com.brbx.design_system.components.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

private const val HeaderKey = "HeaderKey"
private const val SpacerKey = "SpacerKey"

private val RoundedCornersSize = 100.dp

@Composable
fun PlaylistSheet(
    onDismissRequest: () -> Unit,
    visible: Boolean,
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
) {
    if (visible) {
        EchoFlowSheet(
            onDismissRequest = onDismissRequest,
        ) { SheetContent(playlistName, tracks) }
    } else return
}

@Composable
private fun SheetContent(
    playlistName: String,
    tracks: ImmutableList<TrackItem>,
) =
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.micro2),
        contentPadding = PaddingValues(all = mDimens.micro8),
    ) {
        item(HeaderKey) {
            Header(
                title = playlistName,
                description = "${tracks.size} songs",
                onPlayClick = {},
                onMixClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
            )
        }

        item(SpacerKey) {
            Spacer(
                modifier = Modifier
                    .height(mDimens.micro2)
                    .animateItem()
            )
        }

        itemsIndexed(
            items = tracks,
            key = { _, item -> item.id }
        ) { index, track ->
            TrackItem(
                isPlaying = false,
                trackItem = track,
                modifier = Modifier.fillMaxWidth(),
                isFirst = index == 0,
                isLast = index == tracks.lastIndex,
            )
        }
    }

@Composable
private fun Header(
    title: String,
    description: String,
    onPlayClick: () -> Unit,
    onMixClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mDimens.micro8)
    ) {
        HeaderTitle(
            title = title,
            description = description,
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
    description: String,
    modifier: Modifier = Modifier,
) =
    Column(modifier) {
        Text(
            text = title,
            style = mTypography.headlineLarge.copy(
                fontWeight = FontWeight.W700,
                fontFamily = gFlexFontFamily(),
                color = mColors.onBackground,
            )
        )

        Text(
            text = description,
            style = mTypography.bodyMedium.copy(
                color = mColors.onBackground.copy(alpha = 0.6f),
            )
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
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(
                topStart = RoundedCornersSize,
                bottomStart = RoundedCornersSize,
                bottomEnd = mShapes.smallRadius,
                topEnd = mShapes.smallRadius,
            )
        )

        PlaylistButton(
            contentColor = mColors.onTertiary,
            color = mColors.tertiary,
            onClick = onMixClick,
            icon = BoldSolar.VideoAudioSound.Shuffle,
            text = stringResource(DesignComponentsRes.string.playlist_sheet_mix_button_label),
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(
                bottomEnd = RoundedCornersSize,
                topEnd = RoundedCornersSize,
                topStart = mShapes.smallRadius,
                bottomStart = mShapes.smallRadius,
            )
        )
    }

@Composable
private fun PlaylistButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    modifier: Modifier,
    shape: Shape,
    color: Color = mColors.primary,
    contentColor: Color = mColors.onPrimary
) =
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = onClick,
        modifier = modifier,
        shape = shape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(mDimens.micro4),
            modifier = Modifier.padding(vertical = mDimens.micro4)
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                EchoFlowIcon(
                    imageVector = icon,
                    modifier = Modifier.size(mDimens.macro1)
                )

                Text(
                    text = text,
                    style = mTypography.labelLarge,
                    color = LocalContentColor.current
                )
            }
        }
    }