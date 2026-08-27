package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.onboarding.model.BaseAuthPage
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal actual fun OnboardingScaffold() {
    val viewModel = koinViewModel<OnboardingViewModel<OnboardingPage>>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    OnboardingScaffoldInternal(
        state = state,
        onPageAction = { page -> handlePageAction(page) }
    )
}

private fun handlePageAction(page: OnboardingPage) {
    if (page is BaseAuthPage) {
        TODO()
    }
}