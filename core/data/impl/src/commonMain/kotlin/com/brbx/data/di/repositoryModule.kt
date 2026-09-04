package com.brbx.data.di

import com.brbx.data.repository.UserAuthRepository
import com.brbx.data.repository.UserAuthRepositoryImpl
import com.brbx.data.repository.UserLibraryRepository
import com.brbx.data.repository.UserLibraryRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(constructor = ::UserAuthRepositoryImpl) { bind<UserAuthRepository>() }
    singleOf(constructor = ::UserLibraryRepositoryImpl) { bind<UserLibraryRepository>() }
}