package com.brbx.common_app.di

import com.brbx.common_app.view_model.AppViewModel
import com.brbx.common_app.view_model.AuthStateDelegate
import com.brbx.common_app.view_model.AuthStateDelegateImpl
import com.brbx.common_app.view_model.base.AppMviScope
import com.brbx.core_common.dispatchers.getIoDispatcher
import com.brbx.feature_common.view_model.delegateFactory
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    delegateFactory<AuthStateDelegate, AppMviScope> {
        AuthStateDelegateImpl(
            scope = it,
            getUserAuthStateUseCase = get(),
            dispatcherIo = getIoDispatcher(),
        )
    }
    
    viewModel<AppViewModel>()
}