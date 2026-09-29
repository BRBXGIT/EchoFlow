package com.brbx.onboarding.di

import com.brbx.feature_common.view_model.mviDelegateOf
import com.brbx.onboarding.view_model.Authenticator
import com.brbx.onboarding.view_model.AuthenticatorImpl
import com.brbx.onboarding.view_model.OnboardingViewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.plugin.module.dsl.viewModel

internal expect val viewModelModule: Module

internal fun <T> Module.viewModelModuleInternal() {
    mviDelegateOf(::AuthenticatorImpl) bind Authenticator::class

    viewModel<OnboardingViewModel<T>>()
}
