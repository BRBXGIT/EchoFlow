package com.brbx.onboarding.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage
import kotlin.reflect.KClass

private typealias OnboardingPageRenderer<T> = @Composable (
    page: T,
    onSkip: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier
) -> Unit

@Immutable
internal interface OnboardingRendererRegistry {
    fun <T : OnboardingPage> register(
        clazz: KClass<T>,
        renderer: OnboardingPageRenderer<T>
    )

    @Composable
    fun Render(
        page: OnboardingPage,
        onSkip: () -> Unit,
        onAction: () -> Unit,
        modifier: Modifier = Modifier
    )
}

internal class OnboardingRendererRegistryImpl : OnboardingRendererRegistry {
    private val renderers = mutableMapOf<KClass<out OnboardingPage>, Any>()

    override fun <T : OnboardingPage> register(
        clazz: KClass<T>,
        renderer: OnboardingPageRenderer<T>
    ) {
        renderers[clazz] = renderer
    }

    @Suppress("UNCHECKED_CAST")
    @Composable
    override fun Render(
        page: OnboardingPage,
        onSkip: () -> Unit,
        onAction: () -> Unit,
        modifier: Modifier,
    ) {
        val renderer = renderers[page::class] as? OnboardingPageRenderer<OnboardingPage>
            ?: throw IllegalStateException("No renderer registered for ${page::class}")
        renderer(page, onSkip, onAction, modifier)
    }
}
