package com.brbx.onboarding.view_model.onboarding_page

import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.Action
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.IconCollage
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.description_notifications
import echoflow.feature.onboarding.impl.generated.resources.title_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.title_notifications
import org.jetbrains.compose.resources.StringResource

internal object Notifications : OnboardingPage {
    override val title: StringResource = Res.string.title_notifications
    override val description: StringResource = Res.string.description_notifications
    override val action: Action = Action()
    override val collage: IconCollage = AndroidPagesCollageFactory.notifications
}

internal object BatteryOptimization : OnboardingPage {
    override val title: StringResource = Res.string.title_battery_optimization
    override val description: StringResource = Res.string.description_battery_optimization
    override val action: Action = Action(canSkip = true)
    override val collage: IconCollage = AndroidPagesCollageFactory.batteryOptimization
}