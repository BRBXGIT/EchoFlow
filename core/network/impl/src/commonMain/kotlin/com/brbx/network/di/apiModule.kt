package com.brbx.network.di

import com.brbx.network.api.AuthApi
import com.brbx.network.api.AuthApiImpl
import com.brbx.network.api.RelatedApi
import com.brbx.network.api.RelatedApiImpl
import com.brbx.network.api.UserFeedApi
import com.brbx.network.api.UserFeedApiImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val apiModule = module {
    singleOf(constructor = ::AuthApiImpl) { bind<AuthApi>() }
    singleOf(constructor = ::UserFeedApiImpl) { bind<UserFeedApi>() }
    singleOf(constructor = ::RelatedApiImpl) { bind<RelatedApi>() }
}