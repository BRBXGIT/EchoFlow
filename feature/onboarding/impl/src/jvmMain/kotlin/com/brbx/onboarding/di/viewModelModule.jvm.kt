package com.brbx.onboarding.di

import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.JvmPagesDelegate
import com.brbx.onboarding.view_model.PagesDelegate
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal actual val viewModelModule = module {
    factoryOf(constructor = ::JvmPagesDelegate) { bind<PagesDelegate<OnboardingPage>>() }

    viewModelModuleInternal<OnboardingPage>()
}