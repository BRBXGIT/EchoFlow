package com.brbx.onboarding.composable

import echoflow.feature.onboarding.impl.generated.resources.Res
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.brbx.feature_common.utils.CommonText
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.onboarding_page.AndroidPage
import com.brbx.onboarding.view_model.onboarding_page.CommonPage
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import echoflow.feature.onboarding.impl.generated.resources.label_snackbar_battery_optimization_dialog
import echoflow.feature.onboarding.impl.generated.resources.label_snackbar_battery_optimization_dialog_action
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleScreenEffects(
    effects: SharedFlow<OnboardingEffect>,
    postEffect: (EchoFlowEffect) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {}
    HandleScreenEffectsInternal(effects) { page ->
        handlePageAction(
            page = page,
            handleBatteryOptimization = { p ->
                postEffect(
                    EchoFlowEffect.Snackbar(
                        text = CommonText.Res(value = Res.string.label_snackbar_battery_optimization_dialog),
                        action = EchoFlowEffect.Snackbar.Action(
                            text = CommonText.Res(value = Res.string.label_snackbar_battery_optimization_dialog_action),
                            onClick = { startSettingsIntent(context, permission = p) },
                        )
                    )
                )
            },
            handleNotifications = { p -> launcher.launch(input = p) }
        )
    }
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

@SuppressLint("NewApi")
private fun handlePageAction(
    page: OnboardingPage,
    handleBatteryOptimization: (permission: String) -> Unit,
    handleNotifications: (permission: String) -> Unit
) {
    if (page is AndroidPage) {
        when (page) {
            AndroidPage.BatteryOptimization -> handleBatteryOptimization(page.permission)
            AndroidPage.Notifications -> handleNotifications(page.permission)
        }
    }
    if (page is CommonPage) {
        when (page) {
            CommonPage.Authentication -> TODO()
            CommonPage.Greeting -> TODO()
        }
    }
}