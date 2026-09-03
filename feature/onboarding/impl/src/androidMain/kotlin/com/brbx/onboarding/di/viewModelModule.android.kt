package com.brbx.onboarding.di

import com.brbx.onboarding.model.AndroidPagePayload
import com.brbx.onboarding.view_model.AndroidAuthLinkHandler
import com.brbx.onboarding.view_model.AndroidPagesSource
import com.brbx.onboarding.view_model.AuthLinkHandler
import com.brbx.onboarding.view_model.PagesSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal actual val viewModelModule = module {
    factoryOf(constructor = ::AndroidPagesSource) { bind<PagesSource<AndroidPagePayload>>() }
    factoryOf(constructor = ::AndroidAuthLinkHandler) { bind<AuthLinkHandler>() }

    viewModelModuleInternal<AndroidPagePayload>()
}
