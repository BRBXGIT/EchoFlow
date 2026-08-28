package com.brbx.feature_common.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.brbx.feature_common.view_model.EchoFlowEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
actual fun HandleEchoFlowEffects(
    effects: SharedFlow<EchoFlowEffect>,
    snackbarHost: SnackbarHostState?
) =
    HandleEchoFlowEffectsInternal(
        effects = effects,
        snackbarHost = snackbarHost,
        onOpenLink = { link -> TODO() },
    )