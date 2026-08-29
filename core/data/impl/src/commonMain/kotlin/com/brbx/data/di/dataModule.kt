package com.brbx.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(
        repositoryModule,
        builderModule,
        handlerModule,
        interactorModule,
    )
}