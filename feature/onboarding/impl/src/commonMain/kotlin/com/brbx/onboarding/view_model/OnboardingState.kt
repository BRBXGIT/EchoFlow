package com.brbx.onboarding.view_model

import androidx.compose.runtime.Immutable
import com.brbx.onboarding.view_model.onboarding_page.Authentication
import com.brbx.onboarding.view_model.onboarding_page.Greeting
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage

internal expect fun createPages(): List<OnboardingPage>

internal fun createPagesInternal(
    additional: List<OnboardingPage> = emptyList(),
): List<OnboardingPage> =
    buildList {
        add(Greeting)
        addAll(elements = additional)
        add(Authentication)
    }

@Immutable
internal data class OnboardingState(
    val pages: List<OnboardingPage> = createPages(),
    val pageCount: Int = pages.size,
)