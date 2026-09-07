package com.brbx.data.di

import com.brbx.data.repository.AuthRepository
import com.brbx.data.repository.AuthRepositoryImpl
import com.brbx.data.repository.RelatedRepository
import com.brbx.data.repository.RelatedRepositoryImpl
import com.brbx.data.repository.UserHistoryRepository
import com.brbx.data.repository.UserHistoryRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(constructor = ::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(constructor = ::UserHistoryRepositoryImpl) { bind<UserHistoryRepository>() }
    singleOf(constructor = ::RelatedRepositoryImpl) { bind<RelatedRepository>() }
}
