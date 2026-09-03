package com.brbx.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val platformModule: Module

val dataModule = module {
    includes(
        repositoryModule,
        builderModule,
        handlerModule,
        interactorModule,
        platformModule,
        inversionModule,
    )
}