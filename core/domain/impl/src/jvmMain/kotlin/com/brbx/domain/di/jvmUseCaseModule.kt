package com.brbx.domain.di

import com.brbx.domain.use_case.GetAuthUrlUseCase
import com.brbx.domain.use_case.GetCurrentAuthUrlUseCase
import com.brbx.domain.use_case.GetCurrentAuthUrlUseCaseImpl
import com.brbx.domain.use_case.JvmGetAuthUrlUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val jvmUseCaseModule = module {
    singleOf(constructor = ::JvmGetAuthUrlUseCaseImpl) { bind<GetAuthUrlUseCase>() }
    singleOf(constructor = ::GetCurrentAuthUrlUseCaseImpl) { bind<GetCurrentAuthUrlUseCase>() }
}
