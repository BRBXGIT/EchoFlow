package com.brbx.playlist.di

import com.brbx.playlist.view_model.PlaylistBinder
import com.brbx.playlist.view_model.PlaylistBinderImpl
import com.brbx.playlist.view_model.PlaylistViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    factoryOf(constructor = ::PlaylistBinderImpl) { bind<PlaylistBinder>() }
    viewModel<PlaylistViewModel>()
}