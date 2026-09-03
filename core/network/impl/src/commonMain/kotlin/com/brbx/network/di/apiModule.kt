package com.brbx.network.di

import com.brbx.network.api.AuthApi
import com.brbx.network.api.AuthApiImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val apiModule = module {
    singleOf(constructor = ::AuthApiImpl) { bind<AuthApi>() }
}