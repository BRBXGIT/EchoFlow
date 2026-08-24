package com.brbx.onboarding.view_model

import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import com.brbx.onboarding.view_model.onboarding_page.PermissionChecker

internal actual fun createPages(permissionChecker: PermissionChecker): List<OnboardingPage> =
    createPagesInternal()
