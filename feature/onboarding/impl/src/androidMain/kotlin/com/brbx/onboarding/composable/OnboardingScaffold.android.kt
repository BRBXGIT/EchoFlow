package com.brbx.onboarding.composable

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.brbx.onboarding.page_source.AndroidOnboardingPage
import com.brbx.onboarding.page_source.AuthOnboardingPage
import com.brbx.onboarding.page_source.OnboardingPage
import com.brbx.onboarding.page_source.rememberPageItemsSource

@Composable
internal actual fun OnboardingScaffold() {
    val source = rememberPageItemsSource()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted -> if (granted) source.refreshPages() }
    val context = LocalContext.current

    OnboardingScaffoldInternal(
        source = source,
        onPageAction = { page -> handlePage(page, launcher, context) }
    )
}

private fun handlePage(
    page: OnboardingPage,
    launcher: ActivityResultLauncher<String>,
    context: Context,
) {
    when (page) {
        is AndroidOnboardingPage -> handleAndroidPermissions(launcher, page.permission, context)
        is AuthOnboardingPage -> TODO()
    }
}

@SuppressLint("BatteryLife")
private fun handleAndroidPermissions(
    launcher: ActivityResultLauncher<String>,
    permission: String,
    context: Context,
) {
    when (permission) {
        Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS -> {
            val intent = Intent().apply {
                action = permission
                data = "package:${context.packageName}".toUri()
            }
            context.startActivity(intent)
        }
        else -> launcher.launch(input = permission)
    }
}