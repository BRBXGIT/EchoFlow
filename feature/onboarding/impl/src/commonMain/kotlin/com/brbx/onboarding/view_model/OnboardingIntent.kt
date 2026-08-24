package com.brbx.onboarding.view_model

internal sealed interface OnboardingIntent {
    object RefreshPermissions : OnboardingIntent
}