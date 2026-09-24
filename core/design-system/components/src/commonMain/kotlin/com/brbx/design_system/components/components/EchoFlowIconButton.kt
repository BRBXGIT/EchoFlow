package com.brbx.design_system.components.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.theme.mDimens
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.bold.VideoAudioSound
import dev.chiksmedina.solar.bold.videoaudiosound.Play

@Composable
fun EchoFlowIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    iconSize: Dp = mDimens.macro2,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    onClick: () -> Unit,
) =
    IconButton(
        onClick = onClick,
        interactionSource = interactionSource,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        EchoFlowIcon(imageVector = imageVector, modifier = Modifier.size(iconSize))
    }

@Composable
fun EchoFlowFilledIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    onClick: () -> Unit,
) =
    FilledIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        EchoFlowIcon(imageVector = imageVector)
    }

@Composable
fun EchoFlowFilledTonalIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
    onClick: () -> Unit,
) =
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        EchoFlowIcon(imageVector = imageVector)
    }

@Composable
@EchoFlowPreview
private fun EchoFlowIconButtonPreview() =
    Row(horizontalArrangement = Arrangement.spacedBy(mDimens.micro4)) {
        EchoFlowIconButton(
            imageVector = BoldSolar.VideoAudioSound.Play,
            onClick = {},
        )
        EchoFlowFilledIconButton(
            imageVector = BoldSolar.VideoAudioSound.Play,
            onClick = {},
        )
        EchoFlowFilledTonalIconButton(
            imageVector = BoldSolar.VideoAudioSound.Play,
            onClick = {},
        )
    }
