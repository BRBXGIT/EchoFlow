package com.brbx.onboarding.model

import com.brbx.onboarding.utils.CommonPagesCollageFactory
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.label_authenticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_greeting
import org.jetbrains.compose.resources.StringResource

internal object BaseAuthPage : OnboardingPage {
    override val title: StringResource = Res.string.title_authentication
    override val description: StringResource = Res.string.description_authentication
    override val collage: OnboardingPage.IconCollage = CommonPagesCollageFactory.authentication
    override val action: OnboardingPage.Action = OnboardingPage.Action(
        enabledText = Res.string.label_authenticate_button,
    )
}

internal object BaseGreetingPage : OnboardingPage {
    override val title: StringResource = Res.string.title_greeting
    override val description: StringResource = Res.string.description_greeting
    override val collage: OnboardingPage.IconCollage = CommonPagesCollageFactory.greeting
    override val action: OnboardingPage.Action? = null
}