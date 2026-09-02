package com.brbx.domain.di

import org.koin.dsl.module

internal actual val platformModule = module {
    includes(androidUseCaseModule)
}