package com.brbx.feature_common.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.brbx.feature_common.utils.suspendAsString
import com.brbx.feature_common.view_model.EchoFlowEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun HandleEchoFlowEffects(
    effects: SharedFlow<EchoFlowEffect>,
    snackbarHost: SnackbarHostState? = null,
) =
    LaunchedEffect(key1 = effects) {
        effects.collect { effect ->
            when (effect) {
                is EchoFlowEffect.Snackbar -> snackbarHost?.let {
                    showSnackbar(snackbarHost, snackbar = effect)
                } ?: error("No SnackbarHost provided to HandleEchoFlowEffects")
            }
        }
    }

private fun CoroutineScope.showSnackbar(
    snackbarHost: SnackbarHostState,
    snackbar: EchoFlowEffect.Snackbar,
) {
    launch {
        val result = snackbarHost.showSnackbar(
            message = snackbar.text.suspendAsString(),
            actionLabel = snackbar.action?.text?.suspendAsString(),
            duration = snackbar.duration,
        )
        when (result) {
            SnackbarResult.Dismissed -> snackbar.action?.onDismiss()
            SnackbarResult.ActionPerformed -> snackbar.action?.onClick()
        }
    }
}