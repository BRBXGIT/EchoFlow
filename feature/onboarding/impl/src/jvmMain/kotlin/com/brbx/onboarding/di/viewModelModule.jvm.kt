package com.brbx.onboarding.di

import com.brbx.onboarding.model.DesktopPagePayload
import com.brbx.onboarding.view_model.JvmPagesSource
import com.brbx.onboarding.view_model.PagesSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal actual val viewModelModule = module {
    factoryOf(constructor = ::JvmPagesSource) { bind<PagesSource<DesktopPagePayload>>() }

    viewModelModuleInternal<DesktopPagePayload>()
}
