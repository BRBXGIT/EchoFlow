package com.brbx.common_app.di

import com.brbx.common_app.view_model.AppViewModel
import com.brbx.common_app.view_model.AuthStateDelegate
import com.brbx.common_app.view_model.AuthStateDelegateImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    factoryOf(constructor = ::AuthStateDelegateImpl) { bind<AuthStateDelegate>() }
    
    viewModel<AppViewModel>()
}