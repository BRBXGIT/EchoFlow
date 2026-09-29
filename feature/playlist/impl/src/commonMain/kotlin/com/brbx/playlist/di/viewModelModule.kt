package com.brbx.playlist.di

import com.brbx.feature_common.view_model.mviDelegateOf
import com.brbx.playlist.view_model.PlaylistBinder
import com.brbx.playlist.view_model.PlaylistBinderImpl
import com.brbx.playlist.view_model.PlaylistViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    mviDelegateOf(::PlaylistBinderImpl) bind PlaylistBinder::class

    viewModel<PlaylistViewModel>()
}