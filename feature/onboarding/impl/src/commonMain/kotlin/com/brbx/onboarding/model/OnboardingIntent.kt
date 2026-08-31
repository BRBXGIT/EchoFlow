package com.brbx.onboarding.model

sealed interface OnboardingIntent {
    data object RefreshPages : OnboardingIntent

    data object OpenAuthLink : OnboardingIntent

    @JvmInline value class Authenticate(val deeplink: String) : OnboardingIntent
}