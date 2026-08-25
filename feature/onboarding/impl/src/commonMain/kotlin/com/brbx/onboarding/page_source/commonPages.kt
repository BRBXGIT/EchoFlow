package com.brbx.onboarding.page_source

import androidx.compose.runtime.Immutable
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.label_authnticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_greeting
import org.jetbrains.compose.resources.StringResource

@Immutable
internal data class CommonOnboardingPageItem(
    override val title: StringResource,
    override val description: StringResource,
    override val collage: OnboardingPage.IconCollage,
    override val action: OnboardingPage.Action? = null,
) : OnboardingPage

@Immutable
internal data class AuthOnboardingOnboardingPageItem(
    override val title: StringResource,
    override val description: StringResource,
    override val collage: OnboardingPage.IconCollage,
    override val action: OnboardingPage.Action? = null,
) : OnboardingPage, AuthOnboardingPage

private val greeting = CommonOnboardingPageItem(
    title = Res.string.title_greeting,
    description = Res.string.description_greeting,
    collage = CommonPagesCollageFactory.greetingCollage,
)

private val authentication = AuthOnboardingOnboardingPageItem(
    title = Res.string.title_authentication,
    description = Res.string.description_authentication,
    collage = CommonPagesCollageFactory.authenticationCollage,
    action = OnboardingPage.Action(enabledText = Res.string.label_authnticate_button),
)

internal fun createPagesInternal(
    block: MutableList<OnboardingPage>.() -> Unit = {},
): List<OnboardingPage> =
    buildList {
        add(greeting)
        block()
        add(authentication)
    }
