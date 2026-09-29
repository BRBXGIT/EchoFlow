package com.brbx.home.di

import com.brbx.feature_common.view_model.scopedMviDelegateOf
import com.brbx.home.view_model.FeedLoader
import com.brbx.home.view_model.FeedLoaderImpl
import com.brbx.home.view_model.HomeDj
import com.brbx.home.view_model.HomeDjImpl
import com.brbx.home.view_model.HomeTracksStore
import com.brbx.home.view_model.HomeViewModel
import com.brbx.home.view_model.PlaylistOpener
import com.brbx.home.view_model.PlaylistOpenerImpl
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    scope<HomeViewModel> {
        scopedOf(::HomeTracksStore)
        scopedMviDelegateOf(::PlaylistOpenerImpl) bind PlaylistOpener::class
        scopedMviDelegateOf(::FeedLoaderImpl) bind FeedLoader::class
        scopedMviDelegateOf(::HomeDjImpl) bind HomeDj::class
    }

    viewModel<HomeViewModel>()
}
