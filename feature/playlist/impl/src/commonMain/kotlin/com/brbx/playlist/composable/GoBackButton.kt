package com.brbx.playlist.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.brbx.debug.compose.EchoFlowPreview
import com.brbx.design_system.components.components.EchoFlowFilledTonalIconButton
import com.brbx.design_system.components.utils.rememberIsLargeScreen
import com.brbx.design_system.theme.mDimens
import com.brbx.feature_common.view_model.EchoFlowEffect
import dev.chiksmedina.solar.OutlineSolar
import dev.chiksmedina.solar.outline.Arrows
import dev.chiksmedina.solar.outline.arrows.ArrowLeft

@Composable
internal fun GoBackButton(
    postEffect: (EchoFlowEffect) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!rememberIsLargeScreen()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart,
        ) {
            EchoFlowFilledTonalIconButton(
                modifier = Modifier.padding(top = mDimens.micro8, start = mDimens.micro8),
                imageVector = OutlineSolar.Arrows.ArrowLeft,
                onClick = { postEffect(EchoFlowEffect.NavigateBack) },
            )
        }
    } else return
}

@Composable
@EchoFlowPreview
private fun GoBackButtonPreview() =
    GoBackButton(
        postEffect = {},
    )
