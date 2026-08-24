package com.brbx.onboarding.view_model.onboarding_page

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
internal sealed interface OnboardingAction {
    val text: StringResource
    val canSkip: Boolean
    val isEnabled: Boolean
    val disabledText: StringResource?

    data class Authenticate(
        override val text: StringResource,
        override val canSkip: Boolean = false,
        override val isEnabled: Boolean = true,
        override val disabledText: StringResource? = null,
    ) : OnboardingAction

    data class RequestPermission(
        val permission: String,
        override val text: StringResource,
        override val canSkip: Boolean = false,
        override val isEnabled: Boolean = true,
        override val disabledText: StringResource? = null,
    ) : OnboardingAction
}
