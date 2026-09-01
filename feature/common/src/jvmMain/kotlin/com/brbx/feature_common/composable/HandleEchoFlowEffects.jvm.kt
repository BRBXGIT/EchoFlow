package com.brbx.feature_common.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.brbx.feature_common.view_model.EchoFlowEffect
import kotlinx.coroutines.flow.SharedFlow
import java.awt.Desktop
import java.net.URI

@Composable
actual fun HandleEchoFlowEffects(
    effects: SharedFlow<EchoFlowEffect>,
    snackbarHost: SnackbarHostState?
) =
    HandleEchoFlowEffectsInternal(
        effects = effects,
        snackbarHost = snackbarHost,
        onOpenLink = { openLink(it) },
    )

private fun openLink(link: String) {
    val isSupported =
        Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)
    if (isSupported) Desktop.getDesktop().browse(URI(link))
}