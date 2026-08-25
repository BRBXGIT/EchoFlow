package com.brbx.onboarding.view_model

import androidx.compose.runtime.Immutable
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction

@Immutable
internal sealed interface OnboardingEffect {
    @JvmInline value class HandleAction(val action: OnboardingAction) : OnboardingEffect

    data object NextPage : OnboardingEffect

    data object PreviousPage : OnboardingEffect
}