package com.brbx.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.components.utils.rememberSnackbarHost
import com.brbx.design_system.theme.mColors
import com.brbx.feature_common.composable.HandleEchoFlowEffects
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.OnboardingViewModel
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OnboardingScaffold(
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    val snackbarHost = rememberSnackbarHost()
    HandleEchoFlowEffects(viewModel.effects, snackbarHost)
    HandleScreenEffects(viewModel.screenEffects, viewModel::postEffect)

    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        modifier = Modifier
            .fillMaxSize()
            .background(color = mColors.background)
    ) { innerPadding ->
        OnboardingContent(
            onAction = { action ->
                val effect = OnboardingEffect.HandleAction(action)
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
internal expect fun HandleScreenEffects(
    effects: SharedFlow<OnboardingEffect>,
    postEffect: (EchoFlowEffect) -> Unit,
)

@Composable
internal inline fun HandleScreenEffectsInternal(
    effects: SharedFlow<OnboardingEffect>,
    crossinline handleAction: (action: OnboardingAction) -> Unit,
) =
    LaunchedEffect(key1 = effects) {
        effects.collect { effect ->
            when (effect) {
                is OnboardingEffect.HandleAction -> handleAction(effect.action)
            }
        }
    }