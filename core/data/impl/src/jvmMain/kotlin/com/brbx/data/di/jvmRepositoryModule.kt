package com.brbx.data.di

import com.brbx.data.repository.AuthServerRepository
import com.brbx.data.repository.AuthServerRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val jvmRepositoryModule = module {
    singleOf(constructor = ::AuthServerRepositoryImpl) { bind<AuthServerRepository>() }
}