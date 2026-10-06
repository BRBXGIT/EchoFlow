package com.brbx.network.di

import com.brbx.network.api.AuthApi
import com.brbx.network.api.AuthApiImpl
import com.brbx.network.api.TrackApi
import com.brbx.network.api.TrackApiImpl
import com.brbx.network.api.UserFeedApi
import com.brbx.network.api.UserFeedApiImpl
import com.brbx.network.api.UserHistoryApi
import com.brbx.network.api.UserHistoryApiImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val apiModule = module {
    singleOf(::AuthApiImpl) bind AuthApi::class
    singleOf(::UserHistoryApiImpl) bind UserHistoryApi::class
    singleOf(::UserFeedApiImpl) bind UserFeedApi::class
    singleOf(::TrackApiImpl) bind TrackApi::class
}