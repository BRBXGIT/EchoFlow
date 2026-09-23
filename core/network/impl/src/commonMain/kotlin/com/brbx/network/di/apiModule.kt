package com.brbx.network.di

import com.brbx.network.api.AuthApi
import com.brbx.network.api.AuthApiImpl
import com.brbx.network.api.RelatedApi
import com.brbx.network.api.RelatedApiImpl
import com.brbx.network.api.UserFeedApi
import com.brbx.network.api.UserFeedApiImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val apiModule = module {
    singleOf(::AuthApiImpl) bind AuthApi::class
    singleOf(::UserFeedApiImpl) bind UserFeedApi::class
    singleOf(::RelatedApiImpl) bind RelatedApi::class
}