package com.brbx.home.di

import com.brbx.core_common.dispatchers.getDefaultDispatcher
import com.brbx.feature_common.view_model.delegateFactory
import com.brbx.home.view_model.FeedLoader
import com.brbx.home.view_model.FeedLoaderImpl
import com.brbx.home.view_model.HomeMviScope
import com.brbx.home.view_model.HomeViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    delegateFactory<FeedLoader, HomeMviScope> {
        FeedLoaderImpl(
            scope = it,
            dispatcherDefault = getDefaultDispatcher(),
            userRelatedTracksUseCase = get(),
            userRecentlyTracksUseCase = get(),
        )
    }

    viewModel<HomeViewModel>()
}
