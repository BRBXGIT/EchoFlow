package com.brbx.onboarding.view_model

import androidx.compose.runtime.Immutable
import com.brbx.onboarding.view_model.onboarding_page.CommonPage
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage

internal expect fun createPages(): List<OnboardingPage>

internal fun createPagesInternal(
    additional: List<OnboardingPage> = emptyList(),
): List<OnboardingPage> =
    buildList {
        add(CommonPage.Greeting)
        addAll(elements = additional)
        add(CommonPage.Authentication)
    }

@Immutable
internal data class OnboardingState(
    val pages: List<OnboardingPage> = createPages(),
    val pageCount: Int = pages.size,
)