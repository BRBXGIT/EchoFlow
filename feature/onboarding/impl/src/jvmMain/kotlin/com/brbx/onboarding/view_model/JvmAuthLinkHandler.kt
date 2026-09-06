package com.brbx.onboarding.view_model

import com.brbx.domain.use_case.GetAuthUrlUseCase
import com.brbx.domain.use_case.GetCurrentAuthUrlUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.view_model.base.OnboardingMviScope
import kotlinx.coroutines.CoroutineDispatcher

internal class JvmAuthLinkHandler(
    override val scope: OnboardingMviScope<Any?>,
    private val getAuthUrlUseCase: GetAuthUrlUseCase,
    private val getCurrentAuthUrlUseCase: GetCurrentAuthUrlUseCase,
    private val dispatcherDefault: CoroutineDispatcher,
) : AuthLinkHandler {
    override fun invoke(intent: OnboardingIntent.OpenAuthLink) {
        postEffect(EchoFlowEffect.OpenLink(getAuthUrlUseCase()))
        launchAction(context = dispatcherDefault) {
            val link = getCurrentAuthUrlUseCase()
            scope.dispatchIntent(OnboardingIntent.Authenticate(deeplink = link))
        }
    }
}
