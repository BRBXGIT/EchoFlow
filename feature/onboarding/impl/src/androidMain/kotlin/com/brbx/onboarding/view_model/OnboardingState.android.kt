package com.brbx.onboarding.view_model

import android.os.Build
import com.brbx.onboarding.view_model.onboarding_page.AndroidPage
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage

private val additionalPages = buildList {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        add(AndroidPage.Notifications)
    }
    add(AndroidPage.BatteryOptimization)
}

internal actual fun createPages(): List<OnboardingPage> =
    createPagesInternal(additional = additionalPages)