package com.brbx.onboarding.view_model

import com.brbx.domain.use_case.GetAuthLinkUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.base.OnboardingMviScope
import com.brbx.onboarding.view_model.base.OnboardingViewModelDelegate

internal interface AuthLinkHandler :
    OnboardingViewModelDelegate<OnboardingPage, OnboardingIntent.OpenAuthLink>

internal class AuthLinkHandlerImpl(
    override val scope: OnboardingMviScope<OnboardingPage>,
    private val getAuthLinkUseCase: GetAuthLinkUseCase,
) : AuthLinkHandler {
    override fun invoke(intent: OnboardingIntent.OpenAuthLink) =
        postEffect(EchoFlowEffect.OpenLink(getAuthLinkUseCase()))
}