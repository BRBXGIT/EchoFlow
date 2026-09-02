package com.brbx.onboarding.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

@Immutable
internal data class OnboardingPage<out T>(
    val title: StringResource,
    val description: StringResource,
    val collage: IconCollage,
    val action: Action? = null,
    val payload: T,
) {
    @Immutable
    data class IconCollage(
        val topStart: ImageVector,
        val topEnd: ImageVector,
        val center: ImageVector,
        val bottomStart: ImageVector,
        val bottomEnd: ImageVector,
    )

    @Immutable
    data class Action(
        val canSkip: Boolean = false,
        val enabled: Boolean = true,
        val disabledText: StringResource? = null,
        val isLoading: Boolean = false,
        val enabledText: StringResource,
    )
}
