package com.brbx.data.di

import com.brbx.data.datasource.AuthTokenDataSource
import com.brbx.data.datasource.AuthTokenDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::AuthTokenDataSourceImpl) bind AuthTokenDataSource::class
}
