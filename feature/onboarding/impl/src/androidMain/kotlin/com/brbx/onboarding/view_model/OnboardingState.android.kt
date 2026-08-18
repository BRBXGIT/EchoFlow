package com.brbx.onboarding.view_model

import com.brbx.onboarding.view_model.onboarding_page.BatteryOptimization
import com.brbx.onboarding.view_model.onboarding_page.Notifications
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage

private val additionalPages = listOf(Notifications, BatteryOptimization)

internal actual fun createPages(): List<OnboardingPage> =
    createPagesInternal(additional = additionalPages)