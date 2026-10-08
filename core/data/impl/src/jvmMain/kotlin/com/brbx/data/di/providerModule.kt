package com.brbx.data.di

import com.brbx.data.provider.PlayerProvider
import com.brbx.data.provider.PlayerProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val providerModule = module {
    singleOf(::PlayerProviderImpl) bind PlayerProvider::class
}