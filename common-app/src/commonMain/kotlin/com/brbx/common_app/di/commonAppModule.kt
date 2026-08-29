package com.brbx.common_app.di

import org.koin.dsl.module

internal val commonAppModule = module {
    includes(
        viewModelModule,
    )
}