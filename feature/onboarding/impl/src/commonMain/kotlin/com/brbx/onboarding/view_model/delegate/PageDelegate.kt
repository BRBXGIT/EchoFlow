package com.brbx.onboarding.view_model.delegate

import com.brbx.feature_common.EchoFlowEffect
import com.brbx.feature_common.EchoFlowMviDelegate
import com.brbx.mvicore.contracts.MviScope
import com.brbx.mvicore.helpers.reduceIf
import com.brbx.onboarding.view_model.model.OnboardingIntent
import com.brbx.onboarding.view_model.model.OnboardingState

internal interface PageDelegate : EchoFlowMviDelegate<OnboardingState, OnboardingIntent.ChangePage>

internal class PageDelegateImpl(
    override val scope: MviScope<OnboardingState, EchoFlowEffect, OnboardingIntent>,
) : PageDelegate {

    override fun invoke(intent: OnboardingIntent.ChangePage) {
        interactWithPage(intent.forward)
    }

    private fun interactWithPage(forward: Boolean) {
        reduceIf(
            condition = forward,
            onElse = { copy(currentPageIndex = currentPageIndex - 1) }
        ) {
            copy(currentPageIndex = currentPageIndex + 1)
        }
    }
}