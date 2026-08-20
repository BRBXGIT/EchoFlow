package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import com.brbx.onboarding.view_model.onboarding_page.OnboardingAction
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleScreenEffects(
    effects: SharedFlow<OnboardingEffect>,
    postEffect: (EchoFlowEffect) -> Unit,
) =
    HandleScreenEffectsInternal(effects) { action ->
        when (action) {
            is OnboardingAction.Authenticate -> TODO()
            is OnboardingAction.NextPage -> Unit
            is OnboardingAction.RequestPermission -> Unit
        }
    }