package com.brbx.domain.di

import com.brbx.domain.use_case.AndroidGetAuthUrlUseCaseImpl
import com.brbx.domain.use_case.GetAuthUrlUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val androidUseCaseModule = module {
    singleOf(constructor = ::AndroidGetAuthUrlUseCaseImpl) { bind<GetAuthUrlUseCase>() }
}
