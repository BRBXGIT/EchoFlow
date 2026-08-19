package com.brbx.onboarding.composable

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.onboarding_page.AndroidPage
import com.brbx.onboarding.view_model.onboarding_page.Authentication
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleScreenEffects(effects: SharedFlow<OnboardingEffect>) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {}
    HandleScreenEffectsInternal(effects) { page ->
        handlePageAction(
            page = page,
            handleBatteryOptimization = { p -> startSettingsIntent(context, permission = p) },
            handleNotifications = { p -> launcher.launch(input = p) }
        )
    }
}

private fun startSettingsIntent(context: Context, permission: String) {
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
    if (page is Authentication) {
        // TODO
    }
}