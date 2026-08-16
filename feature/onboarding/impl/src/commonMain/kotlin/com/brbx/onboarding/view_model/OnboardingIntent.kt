package com.brbx.onboarding.view_model

internal sealed interface OnboardingIntent {
    @JvmInline value class ChangePage(val forward: Boolean) : OnboardingIntent
}