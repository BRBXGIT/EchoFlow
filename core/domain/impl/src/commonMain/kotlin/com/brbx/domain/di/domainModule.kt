package com.brbx.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val platformModule: Module

val domainModule = module {
    includes(useCaseModule)
}