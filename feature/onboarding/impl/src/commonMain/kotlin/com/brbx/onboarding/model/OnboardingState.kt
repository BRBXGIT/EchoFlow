package com.brbx.onboarding.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class OnboardingState<T : OnboardingPage>(
    val pages: List<T> = emptyList(),
    val loading: Boolean = false,
)
