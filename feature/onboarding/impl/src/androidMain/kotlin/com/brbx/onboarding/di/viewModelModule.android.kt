package com.brbx.onboarding.di

import com.brbx.onboarding.model.AndroidPage
import com.brbx.onboarding.view_model.AndroidPagesDelegate
import com.brbx.onboarding.view_model.PagesDelegate
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal actual val viewModelModule = module {
    factoryOf(constructor = ::AndroidPagesDelegate) { bind<PagesDelegate<AndroidPage>>() }

    viewModelModuleInternal<AndroidPage>()
}