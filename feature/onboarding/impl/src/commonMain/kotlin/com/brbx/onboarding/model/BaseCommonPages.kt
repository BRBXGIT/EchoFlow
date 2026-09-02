package com.brbx.onboarding.model

import com.brbx.onboarding.utils.CommonPagesCollageFactory
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.label_authenticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_greeting

internal fun <T> createGreetingPage(payload: T) = OnboardingPage(
    title = Res.string.title_greeting,
    description = Res.string.description_greeting,
    collage = CommonPagesCollageFactory.greeting,
    action = null,
    payload = payload,
)

internal fun <T> createAuthPage(payload: T) = OnboardingPage(
    title = Res.string.title_authentication,
    description = Res.string.description_authentication,
    collage = CommonPagesCollageFactory.authentication,
    action = OnboardingPage.Action(
        enabledText = Res.string.label_authenticate_button,
    ),
    payload = payload,
)
