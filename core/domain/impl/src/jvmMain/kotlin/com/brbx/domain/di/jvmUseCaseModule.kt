package com.brbx.domain.di

import com.brbx.domain.use_case.GetAuthLinkUseCase
import com.brbx.domain.use_case.JvmGetAuthLinkUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val jvmUseCaseModule = module {
    singleOf(constructor = ::JvmGetAuthLinkUseCaseImpl) { bind<GetAuthLinkUseCase>() }
}