package com.brbx.network.di

import org.koin.dsl.module

val networkModule = module {
    includes(
        apiClientModule,
        apiModule,
    )
}