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
import org.jetbrains.compose.resources.StringResource

private val defaultAction = OnboardingPage.Action(
    disabledText = Res.string.label_action_button_permission_granted,
    enabledText = Res.string.label_action_button_permission,
)

internal data object AndroidGreeting : BaseGreetingPage(), AndroidPage

internal data object AndroidAuth : BaseAuthPage(), AndroidPage

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal data class Notifications(
    override val permission: String? = Manifest.permission.POST_NOTIFICATIONS,
    override val title: StringResource = Res.string.title_notifications,
    override val description: StringResource = Res.string.description_notifications,
    override val collage: OnboardingPage.IconCollage = AndroidPagesCollageFactory.notifications,
    override val action: OnboardingPage.Action? = defaultAction,
) : AndroidPage {
    override fun withEnabledAction(isEnabled: Boolean) =
        copy(action = action?.copy(enabled = isEnabled))
}

@SuppressLint("BatteryLife")
internal data class BatteryOptimization(
    override val permission: String? = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
    override val title: StringResource = Res.string.title_battery_optimization,
    override val description: StringResource = Res.string.description_battery_optimization,
    override val collage: OnboardingPage.IconCollage = AndroidPagesCollageFactory.batteryOptimization,
    override val action: OnboardingPage.Action? = defaultAction.copy(canSkip = true),
) : Special {
    override fun withEnabledAction(isEnabled: Boolean) =
        copy(action = action?.copy(enabled = isEnabled))

    override fun isGranted(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    override fun ask(context: Context) {
        val intent = Intent().apply {
            action = permission
            data = "package:${context.packageName}".toUri()
        }
        context.startActivity(intent)
    }
}