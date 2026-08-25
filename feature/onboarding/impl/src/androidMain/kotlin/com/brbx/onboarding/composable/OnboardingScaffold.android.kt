package com.brbx.onboarding.composable

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.brbx.feature_common.utils.CommonText
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.OnboardingIntent
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.label_snackbar_battery_optimization_dialog
import echoflow.feature.onboarding.impl.generated.resources.label_snackbar_battery_optimization_dialog_action
import kotlinx.coroutines.flow.SharedFlow

@SuppressLint("BatteryLife")
@Composable
internal actual fun HandleScreenEffects(
    screenEffects: SharedFlow<OnboardingEffect>,
    pagerState: PagerState,
    postEffect: (EchoFlowEffect) -> Unit,
    dispatchIntent: (OnboardingIntent) -> Unit,
) {
    val context = LocalContext.current
    CheckPermissions { dispatchIntent(OnboardingIntent.RefreshPermissions) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { dispatchIntent(OnboardingIntent.RefreshPermissions) }

    HandleScreenEffectsInternal(screenEffects, pagerState) { action ->
        when (action) {
            is OnboardingAction.Authenticate -> TODO("Navigate to Auth")
            is OnboardingAction.RequestPermission -> {
                if (action.permission == Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) {
                    postSettingsSnackbar(action.permission, context, postEffect)
                } else {
                    launcher.launch(input = action.permission)
                }
            }
        }
    }
}

@Composable
private fun CheckPermissions(onRefresh: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onRefresh()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

private fun postSettingsSnackbar(
    permission: String,
    context: Context,
    postEffect: (EchoFlowEffect) -> Unit
) {
    postEffect(
        EchoFlowEffect.Snackbar(
            text = CommonText.Res(value = Res.string.label_snackbar_battery_optimization_dialog),
            action = EchoFlowEffect.Snackbar.Action(
                text = CommonText.Res(value = Res.string.label_snackbar_battery_optimization_dialog_action),
                onClick = { startSettingsIntent(context, permission = permission) },
            )
        )
    )
}

private fun startSettingsIntent(
    context: Context,
    permission: String,
) {
    val intent = Intent().apply {
        action = permission
        data = "package:${context.packageName}".toUri()
    }
    context.startActivity(intent)
}
