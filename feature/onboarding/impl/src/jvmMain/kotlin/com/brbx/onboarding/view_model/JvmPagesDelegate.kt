package com.brbx.onboarding.view_model

import com.brbx.mvi_core.helpers.reduce
import com.brbx.onboarding.model.BaseAuthPage
import com.brbx.onboarding.model.BaseGreetingPage
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.base.OnboardingMviScope

internal class JvmPagesDelegate(
    override val scope: OnboardingMviScope<OnboardingPage>,
) : PagesDelegate<OnboardingPage> {
    override fun invoke(intent: OnboardingIntent.RefreshPages) =
        reduce { copy(pages = buildPages()) }

    private fun buildPages(): List<OnboardingPage> =
        buildList {
            add(BaseGreetingPage)
            add(BaseAuthPage)
        }
}