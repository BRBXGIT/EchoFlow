package com.brbx.onboarding.di

import com.brbx.core_common.dispatchers.getDefaultDispatcher
import com.brbx.feature_common.view_model.delegateFactory
import com.brbx.onboarding.model.DesktopPagePayload
import com.brbx.onboarding.view_model.AuthLinkHandler
import com.brbx.onboarding.view_model.JvmAuthLinkHandler
import com.brbx.onboarding.view_model.JvmPagesSource
import com.brbx.onboarding.view_model.PagesSource
import com.brbx.onboarding.view_model.OnboardingMviScope
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal actual val viewModelModule = module {
    factoryOf(constructor = ::JvmPagesSource) { bind<PagesSource<DesktopPagePayload>>() }
    delegateFactory<AuthLinkHandler, OnboardingMviScope<Any?>> {
        JvmAuthLinkHandler(
            scope = it,
            getAuthUrlUseCase = get(),
            getCurrentAuthUrlUseCase = get(),
            dispatcherDefault = getDefaultDispatcher(),
        )
    }

    viewModelModuleInternal<DesktopPagePayload>()
}
