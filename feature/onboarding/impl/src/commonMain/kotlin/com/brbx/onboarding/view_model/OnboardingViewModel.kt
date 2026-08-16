package com.brbx.onboarding.view_model

import com.brbx.feature_common.EchoFlowViewModel
import com.brbx.mvicore.helpers.shareInWhileSubscribed
import com.brbx.mvicore.helpers.stateInWhileSubscribed

internal class OnboardingViewModel : EchoFlowViewModel<OnboardingState, OnboardingIntent>(
    initialState = OnboardingState(),
) {
    override val state = _state.stateInWhileSubscribed(initialValue = OnboardingState())
    override val effects = _effects.shareInWhileSubscribed()

    override fun dispatchIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.ChangePage -> TODO()
        }
    }
}