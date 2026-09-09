package com.brbx.onboarding.di

import com.brbx.core_common.dispatchers.getDefaultDispatcher
import com.brbx.feature_common.view_model.delegateFactory
import com.brbx.onboarding.view_model.Authenticator
import com.brbx.onboarding.view_model.AuthenticatorImpl
import com.brbx.onboarding.view_model.OnboardingViewModel
import com.brbx.onboarding.view_model.OnboardingMviScope
import org.koin.core.module.Module
import org.koin.plugin.module.dsl.viewModel

internal expect val viewModelModule: Module

internal fun <T> Module.viewModelModuleInternal() {
    delegateFactory<Authenticator, OnboardingMviScope<Any?>> {
        AuthenticatorImpl(
            scope = it,
            authUseCase = get(),
            dispatcherDefault = getDefaultDispatcher(),
        )
    }

    viewModel<OnboardingViewModel<T>>()
}
