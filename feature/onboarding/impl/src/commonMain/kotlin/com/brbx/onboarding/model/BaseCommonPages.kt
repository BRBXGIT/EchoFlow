package com.brbx.onboarding.model

import com.brbx.onboarding.utils.CommonPagesCollageFactory
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.label_authnticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_greeting
import org.jetbrains.compose.resources.StringResource

internal open class BaseAuthPage(
    final override val title: StringResource = Res.string.title_authentication,
    final override val description: StringResource = Res.string.description_authentication,
    final override val collage: OnboardingPage.IconCollage = CommonPagesCollageFactory.authentication,
    final override val action: OnboardingPage.Action? = OnboardingPage.Action(
        enabledText = Res.string.label_authnticate_button,
    ),
) : OnboardingPage

internal open class BaseGreetingPage(
    final override val title: StringResource = Res.string.title_greeting,
    final override val description: StringResource = Res.string.description_greeting,
    final override val collage: OnboardingPage.IconCollage = CommonPagesCollageFactory.greeting,
    final override val action: OnboardingPage.Action? = null,
) : OnboardingPage