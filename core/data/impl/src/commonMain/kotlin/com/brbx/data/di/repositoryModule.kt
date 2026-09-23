package com.brbx.data.di

import com.brbx.data.repository.AuthRepository
import com.brbx.data.repository.AuthRepositoryImpl
import com.brbx.data.repository.RelatedRepository
import com.brbx.data.repository.RelatedRepositoryImpl
import com.brbx.data.repository.UserHistoryRepository
import com.brbx.data.repository.UserHistoryRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::UserHistoryRepositoryImpl) bind UserHistoryRepository::class
    singleOf(::RelatedRepositoryImpl) bind RelatedRepository::class
}
