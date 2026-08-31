package com.brbx.onboarding.di

import com.brbx.core_common.dispatchers.getIoDispatcher
import com.brbx.feature_common.view_model.delegateFactory
import com.brbx.onboarding.model.OnboardingPage
import com.brbx.onboarding.view_model.AuthLinkHandler
import com.brbx.onboarding.view_model.AuthLinkHandlerImpl
import com.brbx.onboarding.view_model.Authenticator
import com.brbx.onboarding.view_model.AuthenticatorImpl
import com.brbx.onboarding.view_model.OnboardingViewModel
import com.brbx.onboarding.view_model.base.OnboardingMviScope
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.plugin.module.dsl.viewModel

internal expect val viewModelModule: Module

internal fun <T : OnboardingPage> Module.viewModelModuleInternal() {
    factoryOf(constructor = ::AuthLinkHandlerImpl) { bind<AuthLinkHandler>() }
    delegateFactory<Authenticator, OnboardingMviScope<OnboardingPage>> {
        AuthenticatorImpl(
            scope = it,
            authUseCase = get(),
            dispatcherIo = getIoDispatcher()
        )
    }

    viewModel<OnboardingViewModel<T>>()
}