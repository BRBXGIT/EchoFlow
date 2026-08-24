package com.brbx.onboarding.view_model

import androidx.compose.runtime.Stable
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.mvi_core.base.ContainedMviViewModel
import com.brbx.mvi_core.helpers.shareInWhileSubscribed
import com.brbx.mvi_core.helpers.stateInWhileSubscribed

@Stable
internal class OnboardingViewModel :
    ContainedMviViewModel<OnboardingState, EchoFlowEffect, OnboardingEffect, Unit>(
        initialState = OnboardingState()
    ) {

    override val state = _state.stateInWhileSubscribed(initialValue = OnboardingState())
    override val effects = _effects.shareInWhileSubscribed()
    override val screenEffects = _screenEffects.shareInWhileSubscribed()
}