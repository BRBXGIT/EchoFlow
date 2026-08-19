package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.onboarding_page.Authentication
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleScreenEffects(effects: SharedFlow<OnboardingEffect>) =
    HandleScreenEffectsInternal(effects) { page ->

    }

private inline fun handleAuth(
    page: OnboardingPage,
    block: () -> Unit,
) {
    if (page is Authentication) {
        block()
    }
}