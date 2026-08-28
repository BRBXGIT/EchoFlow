package com.brbx.onboarding.view_model

import com.brbx.domain.use_case.GetAuthLinkUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.OnboardingState

internal interface AuthDelegate :
    EchoFlowMviDelegate<OnboardingState<OnboardingPage>, OnboardingIntent.Authenticate, Unit>

internal class AuthDelegateImpl(
    override val scope: EchoFlowMviScope<OnboardingState<OnboardingPage>, OnboardingIntent, Unit>,
    private val getAuthLinkUseCase: GetAuthLinkUseCase,
) : AuthDelegate {
    override fun invoke(intent: OnboardingIntent.Authenticate) {
        val link = getAuthLinkUseCase()
        postEffect(EchoFlowEffect.OpenLink(link))
    }
}