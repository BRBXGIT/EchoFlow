package com.brbx.onboarding.di

import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.core.module.Module
import org.koin.plugin.module.dsl.viewModel

internal expect val viewModelModule: Module

internal fun <T : OnboardingPage> Module.viewModelModuleInternal() {
    viewModel<OnboardingViewModel<T>>()
}