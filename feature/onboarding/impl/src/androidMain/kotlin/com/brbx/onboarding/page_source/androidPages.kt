package com.brbx.onboarding.page_source

import android.Manifest
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.description_notifications
import echoflow.feature.onboarding.impl.generated.resources.label_action_button_permission
import echoflow.feature.onboarding.impl.generated.resources.label_action_button_permission_granted
import echoflow.feature.onboarding.impl.generated.resources.title_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.title_notifications
import org.jetbrains.compose.resources.StringResource

internal data class AndroidOnboardingPageItem(
    override val title: StringResource,
    override val description: StringResource,
    override val permission: String,
    override val collage: OnboardingPage.IconCollage,
    override val action: OnboardingPage.Action? = null,
) : OnboardingPage, AndroidOnboardingPage

private val basicAction = OnboardingPage.Action(
    enabledText = Res.string.label_action_button_permission,
    disabledText = Res.string.label_action_button_permission_granted,
)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal val notifications = AndroidOnboardingPageItem(
    title = Res.string.title_notifications,
    description = Res.string.description_notifications,
    permission = Manifest.permission.POST_NOTIFICATIONS,
    collage = AndroidPagesCollageFactory.notificationsCollage,
    action = basicAction,
)

internal val batteryOptimization = AndroidOnboardingPageItem(
    title = Res.string.title_battery_optimization,
    description = Res.string.description_battery_optimization,
    permission = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
    collage = AndroidPagesCollageFactory.batteryOptimizationCollage,
    action = basicAction.copy(canSkip = true),
)


