package com.brbx.home.di

import com.brbx.feature_common.view_model.delegateOf
import com.brbx.home.view_model.FeedLoader
import com.brbx.home.view_model.FeedLoaderImpl
import com.brbx.home.view_model.HomeViewModel
import com.brbx.home.view_model.PlaylistOpener
import com.brbx.home.view_model.PlaylistOpenerImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    delegateOf(::PlaylistOpenerImpl).bind<PlaylistOpener>()
    delegateOf(::FeedLoaderImpl).bind<FeedLoader>()

    viewModel<HomeViewModel>()
}
