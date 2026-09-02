package com.brbx.onboarding.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class OnboardingState<T>(
    val pages: List<OnboardingPage<T>> = emptyList(),
    val loading: Boolean = false,
)
