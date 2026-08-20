package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.onboarding.view_model.OnboardingEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal actual fun HandleScreenEffects(
    effects: SharedFlow<OnboardingEffect>,
    postEffect: (EchoFlowEffect) -> Unit,
) =
    HandleScreenEffectsInternal(effects) { page ->

    }