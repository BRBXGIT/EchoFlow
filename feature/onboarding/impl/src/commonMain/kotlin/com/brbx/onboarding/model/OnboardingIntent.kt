package com.brbx.onboarding.model

sealed interface OnboardingIntent {
    data object RefreshPages : OnboardingIntent

    data object Authenticate : OnboardingIntent
}