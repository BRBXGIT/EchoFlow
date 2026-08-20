package com.brbx.onboarding.composable

import com.brbx.onboarding.view_model.onboarding_page.StandardOnboardingPage
import org.koin.dsl.module

internal val composableModule = module {
    single<OnboardingRendererRegistry> {
        OnboardingRendererRegistryImpl().apply {
            register(StandardOnboardingPage::class) { page, skip, action, mod ->
                StandardPageRenderer(page, skip, action, mod)
            }
        }
    }
}