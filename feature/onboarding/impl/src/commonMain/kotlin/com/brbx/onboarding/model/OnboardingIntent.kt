package com.brbx.onboarding.model

sealed interface OnboardingIntent {
    data object RefreshPages : OnboardingIntent

    data object Authenticate : OnboardingIntent

    @JvmInline value class HandleAuthDeeplink(val deeplink: String) : OnboardingIntent
}