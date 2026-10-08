package com.brbx.data.di

import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.datasource.AuthTokenDataSourceImpl
import com.brbx.network.storage.AuthTokenStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::AuthTokenDataSourceImpl) binds arrayOf(AuthTokenDataSource::class, AuthTokenStorage::class)
}
