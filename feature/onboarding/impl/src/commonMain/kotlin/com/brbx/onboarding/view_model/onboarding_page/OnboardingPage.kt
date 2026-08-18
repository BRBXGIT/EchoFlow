package com.brbx.onboarding.view_model.onboarding_page

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.label_action_button
import org.jetbrains.compose.resources.StringResource

@Immutable
internal interface OnboardingPage {
    val title: StringResource
    val description: StringResource
    val collage: IconCollage
    val action: Action?

    data class IconCollage(
        val topStart: ImageVector,
        val topEnd: ImageVector,
        val center: ImageVector,
        val bottomStart: ImageVector,
        val bottomEnd: ImageVector,
    )

    data class Action(
        val text: StringResource = Res.string.label_action_button,
        val canSkip: Boolean = false,
    )
}