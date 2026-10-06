package com.brbx.data.di

import com.brbx.data.loader.TrackStreamsLoader
import com.brbx.data.loader.TrackStreamsLoaderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val loaderModule = module {
    singleOf(::TrackStreamsLoaderImpl) bind TrackStreamsLoader::class
}