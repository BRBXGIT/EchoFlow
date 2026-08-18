package com.brbx.onboarding.view_model

import androidx.compose.runtime.Stable
import com.brbx.feature_common.EchoFlowViewModel
import com.brbx.mvicore.helpers.shareInWhileSubscribed
import com.brbx.mvicore.helpers.stateInWhileSubscribed
import com.brbx.onboarding.view_model.model.OnboardingState

@Stable
internal class OnboardingViewModel : EchoFlowViewModel<OnboardingState, Unit>(
    initialState = OnboardingState(),
) {
    override val state = _state.stateInWhileSubscribed(initialValue = OnboardingState())
    override val effects = _effects.shareInWhileSubscribed()
}