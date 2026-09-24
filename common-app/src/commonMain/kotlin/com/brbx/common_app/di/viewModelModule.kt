package com.brbx.common_app.di

import com.brbx.common_app.view_model.AppMviScope
import com.brbx.common_app.view_model.AppViewModel
import com.brbx.common_app.view_model.AuthStateDelegate
import com.brbx.common_app.view_model.AuthStateDelegateImpl
import com.brbx.core_common.dispatchers.getIoDispatcher
import com.brbx.feature_common.view_model.viewModelDelegate
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    viewModelDelegate<AuthStateDelegate, AppMviScope> { scope ->
        AuthStateDelegateImpl(
            scope = scope,
            getUserAuthStateUseCase = get(),
            dispatcherIo = getIoDispatcher(),
        )
    }

    viewModel<AppViewModel>()
}