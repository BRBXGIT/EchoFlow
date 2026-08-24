package com.brbx.onboarding.view_model

import android.os.Build
import com.brbx.onboarding.view_model.onboarding_page.AndroidPage
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import com.brbx.onboarding.view_model.onboarding_page.PermissionChecker

internal actual fun createPages(permissionChecker: PermissionChecker): List<OnboardingPage> =
    createPagesInternal(
        additional = buildList {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(AndroidPage.notifications(permissionChecker))
            }
            add(AndroidPage.batteryOptimization(permissionChecker))
        }
    )
