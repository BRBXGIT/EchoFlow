package com.brbx.network.di

import com.brbx.network.client.ApiClientProvider
import com.brbx.network.client.ApiClientProviderImpl
import com.brbx.network.client.AuthApiClientProvider
import com.brbx.network.client.AuthApiClientProviderImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val apiClientModule = module {
    singleOf(constructor = ::AuthApiClientProviderImpl) { bind<AuthApiClientProvider>() }
    singleOf(constructor = ::ApiClientProviderImpl) { bind<ApiClientProvider>() }
}