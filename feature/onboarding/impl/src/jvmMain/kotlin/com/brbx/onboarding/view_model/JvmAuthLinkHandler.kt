package com.brbx.onboarding.view_model

import com.brbx.domain.use_case.GetAuthLinkUseCase
import com.brbx.domain.use_case.GetCurrentAuthUrlUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.view_model.base.OnboardingMviScope

internal class JvmAuthLinkHandler(
    override val scope: OnboardingMviScope<Any?>,
    private val getAuthLinkUseCase: GetAuthLinkUseCase,
    private val getCurrentAuthUrlUseCase: GetCurrentAuthUrlUseCase,
) : AuthLinkHandler {
    override fun invoke(intent: OnboardingIntent.OpenAuthLink) {
        postEffect(EchoFlowEffect.OpenLink(getAuthLinkUseCase()))
        launchAction {
            val link = getCurrentAuthUrlUseCase()
            scope.dispatchIntent(OnboardingIntent.Authenticate(deeplink = link))
        }
    }
}