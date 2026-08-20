package com.brbx.onboarding.view_model.onboarding_page

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.Action
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.IconCollage
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.description_notifications
import echoflow.feature.onboarding.impl.generated.resources.label_action_button
import echoflow.feature.onboarding.impl.generated.resources.title_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.title_notifications
import org.jetbrains.compose.resources.StringResource

internal sealed interface AndroidPage : OnboardingPage {
    val permission: String

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object Notifications : AndroidPage {
        override val title: StringResource = Res.string.title_notifications
        override val description: StringResource = Res.string.description_notifications
        override val action: Action = Action(text = Res.string.label_action_button)
        override val collage: IconCollage = AndroidPagesCollageFactory.notifications
        override val permission: String = Manifest.permission.POST_NOTIFICATIONS
    }

    object BatteryOptimization : AndroidPage {
        override val title: StringResource = Res.string.title_battery_optimization
        override val description: StringResource = Res.string.description_battery_optimization
        override val action: Action = Action(canSkip = true, text = Res.string.label_action_button)
        override val collage: IconCollage = AndroidPagesCollageFactory.batteryOptimization
        override val permission: String = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
    }
}