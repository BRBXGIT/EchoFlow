package com.brbx.onboarding.view_model

import androidx.compose.runtime.Stable
import com.brbx.mvicore.base.ContainedMviViewModel
import com.brbx.mvicore.helpers.shareInWhileSubscribed
import com.brbx.mvicore.helpers.stateInWhileSubscribed

@Stable
internal class OnboardingViewModel : ContainedMviViewModel<OnboardingState, OnboardingEffect, Unit>(
    initialState = OnboardingState(),
) {
    override val state = _state.stateInWhileSubscribed(initialValue = OnboardingState())
    override val effects = _effects.shareInWhileSubscribed()
}