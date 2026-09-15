package com.brbx.design_system.components.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.brbx.debug.compose.EchoFlowPreview
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.bold.VideoAudioSound
import dev.chiksmedina.solar.bold.videoaudiosound.Play

@Composable
fun EchoFlowIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) =
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier,
        tint = tint,
    )

@Composable
@EchoFlowPreview
private fun EchoFlowIconPreview() =
    EchoFlowIcon(
        imageVector = BoldSolar.VideoAudioSound.Play,
    )
