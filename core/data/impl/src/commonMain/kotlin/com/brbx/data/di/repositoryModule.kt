package com.brbx.data.di

import com.brbx.data.repository.AuthRepository
import com.brbx.data.repository.AuthRepositoryImpl
import com.brbx.data.repository.UserFeedRepository
import com.brbx.data.repository.UserFeedRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(constructor = ::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(constructor = ::UserFeedRepositoryImpl) { bind<UserFeedRepository>() }
}
