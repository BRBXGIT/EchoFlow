package com.brbx.onboarding.view_model

import com.brbx.domain.model.onException
import com.brbx.domain.model.onSuccess
import com.brbx.domain.use_case.AuthenticateUserUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.feature_common.view_model.sendRetrySnackbar
import com.brbx.home.HomeRoute
import com.brbx.mvi_core.helpers.launchAction
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.mvi_core.helpers.reduce
import com.brbx.onboarding.model.BaseAuthPage
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.base.OnboardingMviScope
import com.brbx.onboarding.view_model.base.OnboardingViewModelDelegate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

internal interface Authenticator :
    OnboardingViewModelDelegate<OnboardingPage, OnboardingIntent.Authenticate>

internal class AuthenticatorImpl(
    override val scope: OnboardingMviScope<OnboardingPage>,
    private val authUseCase: AuthenticateUserUseCase,
    private val dispatcherIo: CoroutineDispatcher,
) : Authenticator {
    override fun invoke(intent: OnboardingIntent.Authenticate) {
        launchAction(context = dispatcherIo) {
            reduce { copy(loading = true) }
            delay(duration = 1_500.milliseconds) // Show user that something loading (antipattern)
            authUseCase(rawUri = intent.deeplink)
                .onSuccess {
                    postEffect(EchoFlowEffect.Navigate(key = HomeRoute))
                    postEffect(EchoFlowEffect.DropPreviousNavKey)
                } onException { e ->
                    sendRetrySnackbar(e, dismissable = true) { invoke(intent) }
                }
            reduce { copy(loading = false) }
        }
    }
}