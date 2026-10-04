package com.brbx.domain.di

import com.brbx.domain.use_case.GetAuthUrlUseCase
import com.brbx.domain.use_case.GetCurrentAuthUrlUseCase
import com.brbx.domain.use_case.GetCurrentAuthUrlUseCaseImpl
import com.brbx.domain.use_case.JvmGetAuthUrlUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val jvmUseCaseModule = module {
    factoryOf(::JvmGetAuthUrlUseCaseImpl) bind GetAuthUrlUseCase::class
    factoryOf(::GetCurrentAuthUrlUseCaseImpl) bind GetCurrentAuthUrlUseCase::class
}
