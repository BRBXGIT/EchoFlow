package com.brbx.data.di

import org.koin.dsl.module

internal actual val platformModule = module {
    includes(jvmRepositoryModule)
}