package com.brbx.data.di

import com.brbx.data.storage.AuthTokenStorageImpl
import com.brbx.network.storage.AuthTokenStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val storageModule = module {
    singleOf(::AuthTokenStorageImpl) bind AuthTokenStorage::class
}
