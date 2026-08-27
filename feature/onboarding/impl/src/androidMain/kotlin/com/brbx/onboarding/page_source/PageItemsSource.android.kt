package com.brbx.onboarding.page_source

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

private class AndroidPageItemsSource(
    private val context: Context,
) : PageItemsSource {

    private val refreshTrigger = MutableStateFlow(value = 0)

    override fun createPages(): Flow<List<OnboardingPage>> = refreshTrigger.map {
        buildPages()
    }

    override fun refreshPages() {
        refreshTrigger.update { it + 1 }
    }

    private fun buildPages(): List<OnboardingPage> =
        createPagesInternal {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(notifications.updateGranted(context))
            }
            add(batteryOptimization.updateGranted(context))
        }

    private fun AndroidOnboardingPageItem.updateGranted(context: Context): OnboardingPage {
        val isGranted = isPermissionGranted(context, this.permission)
        val updatedAction = this.action?.copy(enabled = !isGranted)
        return this.copy(action = updatedAction)
    }

    @SuppressLint("BatteryLife")
    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        return when (permission) {
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS -> {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                powerManager.isIgnoringBatteryOptimizations(context.packageName)
            }
            else -> {
                ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED
            }
        }
    }
}

@Composable
internal actual fun rememberPageItemsSource(): PageItemsSource {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val source = remember(key1 = context) {
        AndroidPageItemsSource(context)
    }

    DisposableEffect(key1 = lifecycleOwner, key2 = source) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                source.refreshPages()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    return source
}