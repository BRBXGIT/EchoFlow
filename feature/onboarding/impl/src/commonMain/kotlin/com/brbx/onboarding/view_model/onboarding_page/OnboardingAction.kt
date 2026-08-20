package com.brbx.onboarding.view_model.onboarding_page

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
internal sealed interface OnboardingAction {
    val text: StringResource
    val canSkip: Boolean

    data class Authenticate(
        override val text: StringResource,
        override val canSkip: Boolean = false,
    ) : OnboardingAction

    data class RequestPermission(
        val permission: String,
        override val text: StringResource,
        override val canSkip: Boolean = false,
    ) : OnboardingAction

    data class NextPage(
        override val text: StringResource,
        override val canSkip: Boolean = false,
    ) : OnboardingAction
}
