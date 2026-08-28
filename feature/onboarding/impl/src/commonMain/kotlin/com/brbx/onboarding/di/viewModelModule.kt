package com.brbx.onboarding.di

import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.AuthDelegate
import com.brbx.onboarding.view_model.AuthDelegateImpl
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.plugin.module.dsl.viewModel

internal expect val viewModelModule: Module

internal fun <T : OnboardingPage> Module.viewModelModuleInternal() {
    viewModel<OnboardingViewModel<T>>()

    factoryOf(constructor = ::AuthDelegateImpl) { bind<AuthDelegate>() }
}