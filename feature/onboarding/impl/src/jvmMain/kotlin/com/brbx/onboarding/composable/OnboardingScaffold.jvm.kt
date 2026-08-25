package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import com.brbx.onboarding.page_source.AuthOnboardingPage
import com.brbx.onboarding.page_source.OnboardingPage
import com.brbx.onboarding.page_source.rememberPageItemsSource

@Composable
internal actual fun OnboardingScaffold() {
    val source = rememberPageItemsSource()
    OnboardingScaffoldInternal(
        source = source,
        onPageAction = { page -> handleJvmPages(page) }
    )
}

private fun handleJvmPages(page: OnboardingPage) {
    when (page) {
        is AuthOnboardingPage -> TODO()
    }
}