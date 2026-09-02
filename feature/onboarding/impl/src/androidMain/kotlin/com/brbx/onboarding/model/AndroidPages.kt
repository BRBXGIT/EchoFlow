package com.brbx.onboarding.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.brbx.onboarding.utils.AndroidPagesCollageFactory
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.description_notifications
import echoflow.feature.onboarding.impl.generated.resources.label_action_button_permission
import echoflow.feature.onboarding.impl.generated.resources.label_action_button_permission_granted
import echoflow.feature.onboarding.impl.generated.resources.title_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.title_notifications

private val defaultAction = OnboardingPage.Action(
    disabledText = Res.string.label_action_button_permission_granted,
    enabledText = Res.string.label_action_button_permission,
)

internal data object AndroidGreetingPayload : AndroidPagePayload

internal data object AndroidAuthPayload : AndroidPagePayload

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal data class NotificationsPayload(
    override val permission: String? = Manifest.permission.POST_NOTIFICATIONS,
) : AndroidPagePayload

@SuppressLint("BatteryLife")
internal data class BatteryOptimizationPayload(
    override val permission: String? = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
) : SpecialAndroidPagePayload {
    override fun isGranted(context: Context): Boolean =
        (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
            .isIgnoringBatteryOptimizations(context.packageName)

    override fun ask(context: Context) = context.startActivity(
        Intent().apply {
            action = permission
            data = "package:${context.packageName}".toUri()
        },
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal fun createNotificationsPage() = OnboardingPage(
    title = Res.string.title_notifications,
    description = Res.string.description_notifications,
    collage = AndroidPagesCollageFactory.notifications,
    action = defaultAction,
    payload = NotificationsPayload(),
)

@SuppressLint("BatteryLife")
internal fun createBatteryOptimizationPage() = OnboardingPage(
    title = Res.string.title_battery_optimization,
    description = Res.string.description_battery_optimization,
    collage = AndroidPagesCollageFactory.batteryOptimization,
    action = defaultAction.copy(canSkip = true),
    payload = BatteryOptimizationPayload(),
)
