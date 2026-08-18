package com.brbx.onboarding.view_model.onboarding_page

import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.Action
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.IconCollage
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.label_authnticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_greeting
import org.jetbrains.compose.resources.StringResource

internal object Greeting : OnboardingPage {
    override val title: StringResource = Res.string.title_greeting
    override val description: StringResource = Res.string.description_greeting
    override val action: Action? = null
    override val collage: IconCollage = CommonPagesCollageFactory.greetingCollage
}

internal object Authentication : OnboardingPage {
    override val title: StringResource = Res.string.title_authentication
    override val description: StringResource = Res.string.description_authentication
    override val action: Action = Action(text = Res.string.label_authnticate_button)
    override val collage: IconCollage = CommonPagesCollageFactory.authenticationCollage
}