package com.brbx.onboarding.view_model

import androidx.compose.runtime.Immutable
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingState

@Immutable
internal class OnboardingViewModel<T> :
    EchoFlowViewModel<OnboardingState<T>, OnboardingIntent, Unit>(initialState = OnboardingState()) {

    private val pagesSource by injectDelegate<PagesSource<T>>()
    private val authLinkHandler by injectDelegate<AuthLinkHandler>()
    private val authenticator by injectDelegate<Authenticator>()

    init { dispatchIntent(OnboardingIntent.RefreshPages) }

    override fun dispatchIntent(intent: OnboardingIntent) =
        when (intent) {
            is OnboardingIntent.RefreshPages -> pagesSource(intent)
            is OnboardingIntent.OpenAuthLink -> authLinkHandler(intent)
            is OnboardingIntent.Authenticate -> authenticator(intent)
        }
}
