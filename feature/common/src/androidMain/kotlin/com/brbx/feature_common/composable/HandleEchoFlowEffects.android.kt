package com.brbx.feature_common.composable

import android.content.Context
import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.brbx.feature_common.view_model.EchoFlowEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
actual fun HandleEchoFlowEffects(
    effects: SharedFlow<EchoFlowEffect>,
    snackbarHost: SnackbarHostState?
) {
    val context = LocalContext.current
    HandleEchoFlowEffectsInternal(
        effects = effects,
        snackbarHost = snackbarHost,
        onOpenLink = { link -> handleLinkOpening(link, context) },
    )
}

private fun handleLinkOpening(link: String, context: Context) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        link.toUri()
    )
    context.startActivity(intent)
}