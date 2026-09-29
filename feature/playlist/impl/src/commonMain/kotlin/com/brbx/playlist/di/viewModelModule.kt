package com.brbx.playlist.di

import com.brbx.feature_common.view_model.scopedMviDelegateOf
import com.brbx.playlist.view_model.PlaylistBinder
import com.brbx.playlist.view_model.PlaylistBinderImpl
import com.brbx.playlist.view_model.PlaylistDj
import com.brbx.playlist.view_model.PlaylistDjImpl
import com.brbx.playlist.view_model.PlaylistLoader
import com.brbx.playlist.view_model.PlaylistLoaderImpl
import com.brbx.playlist.view_model.PlaylistStore
import com.brbx.playlist.view_model.PlaylistViewModel
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    scope<PlaylistViewModel> {
        scopedOf(::PlaylistStore)
        scopedMviDelegateOf(::PlaylistBinderImpl) bind PlaylistBinder::class
        scopedMviDelegateOf(::PlaylistLoaderImpl) bind PlaylistLoader::class
        scopedMviDelegateOf(::PlaylistDjImpl) bind PlaylistDj::class
    }

    viewModel<PlaylistViewModel>()
}