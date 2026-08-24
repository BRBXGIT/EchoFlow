package com.brbx.onboarding.view_model.onboarding_page

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.description_notifications
import echoflow.feature.onboarding.impl.generated.resources.label_action_button
import echoflow.feature.onboarding.impl.generated.resources.title_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.title_notifications

internal object AndroidPage {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val Notifications = StandardOnboardingPage(
        title = Res.string.title_notifications,
        description = Res.string.description_notifications,
        action = OnboardingAction.RequestPermission(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            text = Res.string.label_action_button
        ),
        collage = AndroidPagesCollageFactory.notifications
    )

    @SuppressLint("BatteryLife")
    val BatteryOptimization = StandardOnboardingPage(
        title = Res.string.title_battery_optimization,
        description = Res.string.description_battery_optimization,
        action = OnboardingAction.RequestPermission(
            permission = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            text = Res.string.label_action_button,
            canSkip = true
        ),
        collage = AndroidPagesCollageFactory.batteryOptimization
    )
}