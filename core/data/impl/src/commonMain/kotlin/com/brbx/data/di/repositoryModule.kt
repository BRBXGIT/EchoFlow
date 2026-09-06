package com.brbx.data.di

import com.brbx.data.repository.UserAuthRepository
import com.brbx.data.repository.UserAuthRepositoryImpl
import com.brbx.data.repository.UserFeedRepository
import com.brbx.data.repository.UserFeedRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(constructor = ::UserAuthRepositoryImpl) { bind<UserAuthRepository>() }
    singleOf(constructor = ::UserFeedRepositoryImpl) { bind<UserFeedRepository>() }
}