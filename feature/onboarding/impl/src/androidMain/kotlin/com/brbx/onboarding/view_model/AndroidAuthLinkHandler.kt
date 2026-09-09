package com.brbx.onboarding.view_model

import com.brbx.domain.use_case.GetAuthUrlUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.onboarding.model.OnboardingIntent

internal class AndroidAuthLinkHandler(
    override val scope: OnboardingMviScope<Any?>,
    private val getAuthUrlUseCase: GetAuthUrlUseCase,
) : AuthLinkHandler {
    override fun invoke(intent: OnboardingIntent.OpenAuthLink) =
        postEffect(EchoFlowEffect.OpenLink(getAuthUrlUseCase()))
}
