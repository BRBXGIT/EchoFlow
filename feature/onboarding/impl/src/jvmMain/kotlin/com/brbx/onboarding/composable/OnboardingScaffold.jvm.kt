package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.onboarding.model.BaseAuthPage
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal actual fun OnboardingScaffold(deeplink: String?) {
    val viewModel = koinViewModel<OnboardingViewModel<OnboardingPage>>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HandleDeeplink(deeplink, viewModel::dispatchIntent)

    OnboardingScaffoldInternal(
        state = state,
        effects = viewModel.effects,
        onPageAction = { page -> handlePageAction(page, viewModel::dispatchIntent) }
    )
}

private fun handlePageAction(
    page: OnboardingPage,
    dispatchIntent: (OnboardingIntent) -> Unit
) {
    if (page is BaseAuthPage) {
        dispatchIntent(OnboardingIntent.OpenAuthLink)
    }
}