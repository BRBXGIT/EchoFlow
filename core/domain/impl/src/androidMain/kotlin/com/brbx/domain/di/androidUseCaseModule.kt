package com.brbx.domain.di

import com.brbx.domain.use_case.AndroidGetAuthUrlUseCaseImpl
import com.brbx.domain.use_case.GetAuthUrlUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val androidUseCaseModule = module {
    factoryOf(::AndroidGetAuthUrlUseCaseImpl) bind GetAuthUrlUseCase::class
}
