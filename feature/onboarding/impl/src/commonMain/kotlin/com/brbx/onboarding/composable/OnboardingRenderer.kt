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
    private val renderers = mutableMapOf<KClass<out OnboardingPage>, OnboardingPageRenderer<OnboardingPage>>()

    override fun <T : OnboardingPage> register(
        clazz: KClass<T>,
        renderer: OnboardingPageRenderer<T>
    ) {
        renderers[clazz] = { page, onSkip, onAction, modifier ->
            @Suppress("UNCHECKED_CAST")
            renderer(page as T, onSkip, onAction, modifier)
        }
    }

    @Composable
    override fun Render(
        page: OnboardingPage,
        onSkip: () -> Unit,
        onAction: () -> Unit,
        modifier: Modifier,
    ) {
        val renderer = renderers[page::class]
            ?: throw IllegalStateException("No renderer registered for ${page::class}")
        renderer(page, onSkip, onAction, modifier)
    }
}
