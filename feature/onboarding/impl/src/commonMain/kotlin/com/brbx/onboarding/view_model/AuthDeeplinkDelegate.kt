package com.brbx.onboarding.view_model

import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.base.OnboardingMviScope
import com.brbx.onboarding.view_model.base.OnboardingViewModelDelegate

internal interface AuthDeeplinkDelegate :
    OnboardingViewModelDelegate<OnboardingPage, OnboardingIntent.HandleAuthDeeplink>

internal class AuthDeeplinkDelegateImpl(
    override val scope: OnboardingMviScope<OnboardingPage>,
) : AuthDeeplinkDelegate {
    override fun invoke(intent: OnboardingIntent.HandleAuthDeeplink) {
        // TODO all in one usecase
    }
}