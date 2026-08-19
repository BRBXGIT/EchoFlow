package com.brbx.onboarding.view_model

import androidx.compose.runtime.Immutable
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage

@Immutable
internal sealed interface OnboardingEffect {
    @JvmInline value class HandlePageAction(val page: OnboardingPage) : OnboardingEffect
}