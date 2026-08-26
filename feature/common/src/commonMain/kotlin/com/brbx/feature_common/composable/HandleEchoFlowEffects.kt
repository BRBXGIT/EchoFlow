package com.brbx.feature_common.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.brbx.feature_common.utils.suspendAsString
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.navigation.echoFlowNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun HandleEchoFlowEffects(
    effects: SharedFlow<EchoFlowEffect>,
    snackbarHost: SnackbarHostState? = null,
) {
    val navigator = echoFlowNavigator
    LaunchedEffect(key1 = effects) {
        effects.collect { effect ->
            when (effect) {
                is EchoFlowEffect.Snackbar -> snackbarHost?.showSnackbar(effect)
                is EchoFlowEffect.NavigateBack -> navigator.navigateBack()
                is EchoFlowEffect.Navigate -> navigator.navigate(effect.key)
            }
        }
    }
}

context(coroutineScope: CoroutineScope)
private fun SnackbarHostState.showSnackbar(
    snackbar: EchoFlowEffect.Snackbar,
) =
    coroutineScope.launch {
        val result = showSnackbar(
            message = snackbar.text.suspendAsString(),
            actionLabel = snackbar.action?.text?.suspendAsString(),
            duration = snackbar.duration,
        )
        when (result) {
            SnackbarResult.Dismissed -> snackbar.action?.onDismiss()
            SnackbarResult.ActionPerformed -> snackbar.action?.onClick()
        }
    }