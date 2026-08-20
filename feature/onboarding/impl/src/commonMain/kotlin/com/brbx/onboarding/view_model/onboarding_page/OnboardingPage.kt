package com.brbx.onboarding.view_model.onboarding_page

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

@Immutable
internal interface OnboardingPage {
    val action: OnboardingAction?
}

@Immutable
internal data class StandardOnboardingPage(
    val title: StringResource,
    val description: StringResource,
    val collage: IconCollage,
    override val action: OnboardingAction? = null,
) : OnboardingPage {

    @Immutable
    data class IconCollage(
        val topStart: ImageVector,
        val topEnd: ImageVector,
        val center: ImageVector,
        val bottomStart: ImageVector,
        val bottomEnd: ImageVector,
    )
}
