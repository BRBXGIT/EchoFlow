package com.brbx.onboarding.view_model

import androidx.compose.runtime.Immutable
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.mvi_core.helpers.shareInWhileSubscribed
import com.brbx.mvi_core.helpers.stateInWhileSubscribed
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.OnboardingState

@Immutable
internal class OnboardingViewModel<T : OnboardingPage> :
    EchoFlowViewModel<OnboardingState<T>, OnboardingIntent, Unit>(initialState = OnboardingState()) {

    private val pagesDelegate by injectDelegate<PagesDelegate<T>>()
    private val authDelegate by injectDelegate<AuthDelegate>()

    init {
        dispatchIntent(OnboardingIntent.RefreshPages)
    }

    override val state = _state.stateInWhileSubscribed(initialValue = OnboardingState())
    override val effects = _effects.shareInWhileSubscribed()
    override val screenEffects = _screenEffects.shareInWhileSubscribed()

    override fun dispatchIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.RefreshPages -> pagesDelegate(intent)
            is OnboardingIntent.Authenticate -> authDelegate(intent)
        }
    }
}