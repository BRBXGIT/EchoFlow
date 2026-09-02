package com.brbx.onboarding.view_model

import com.brbx.mvi_core.helpers.reduce
import com.brbx.onboarding.model.DesktopAuthPayload
import com.brbx.onboarding.model.DesktopGreetingPayload
import com.brbx.onboarding.model.DesktopPagePayload
import com.brbx.onboarding.model.OnboardingIntent
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.model.createAuthPage
import com.brbx.onboarding.model.createGreetingPage
import com.brbx.onboarding.view_model.base.OnboardingMviScope

internal class JvmPagesSource(
    override val scope: OnboardingMviScope<DesktopPagePayload>,
) : PagesSource<DesktopPagePayload> {
    override fun invoke(intent: OnboardingIntent.RefreshPages) =
        reduce { copy(pages = buildPages()) }

    private fun buildPages(): List<OnboardingPage<DesktopPagePayload>> =
        buildList {
            add(createGreetingPage(DesktopGreetingPayload))
            add(createAuthPage(DesktopAuthPayload))
        }
}
