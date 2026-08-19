package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.theme.mColors
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.OnboardingViewModel
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OnboardingScaffold(
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    HandleScreenEffects(viewModel.screenEffects)

    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mColors.background)
    ) { innerPadding ->
        OnboardingContent(
            onOnboardingAction = { page ->
                val effect = OnboardingEffect.HandlePageAction(page)
                viewModel.postScreenEffect(effect)
            },
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        )
    }
}

@Composable
internal expect fun HandleScreenEffects(effects: SharedFlow<OnboardingEffect>)

@Composable
internal inline fun HandleScreenEffectsInternal(
    effects: SharedFlow<OnboardingEffect>,
    crossinline handlePageAction: (page: OnboardingPage) -> Unit,
) =
    LaunchedEffect(key1 = effects) {
        effects.collect { effect ->
            when (effect) {
                is OnboardingEffect.HandlePageAction -> handlePageAction(effect.page)
            }
        }
    }