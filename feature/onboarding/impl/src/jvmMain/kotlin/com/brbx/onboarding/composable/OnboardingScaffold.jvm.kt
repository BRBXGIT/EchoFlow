package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.onboarding_page.Authentication
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleEffects(effects: SharedFlow<OnboardingEffect>) =
    HandleEffectsInternal(effects) { page ->

    }

private inline fun handleAuth(
    page: OnboardingPage,
    block: () -> Unit,
) {
    if (page is Authentication) {
        block()
    }
}