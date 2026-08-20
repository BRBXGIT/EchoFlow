package com.brbx.onboarding.view_model.onboarding_page

import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.label_authnticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_greeting

internal object CommonPage {
    val Greeting = StandardOnboardingPage(
        title = Res.string.title_greeting,
        description = Res.string.description_greeting,
        action = null,
        collage = CommonPagesCollageFactory.greetingCollage
    )

    val Authentication = StandardOnboardingPage(
        title = Res.string.title_authentication,
        description = Res.string.description_authentication,
        action = OnboardingAction.Authenticate(text = Res.string.label_authnticate_button),
        collage = CommonPagesCollageFactory.authenticationCollage
    )
}