package com.brbx.domain.di

import com.brbx.domain.use_case.AndroidGetAuthLinkUseCaseImpl
import com.brbx.domain.use_case.GetAuthLinkUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val androidUseCaseModule = module {
    singleOf(constructor = ::AndroidGetAuthLinkUseCaseImpl) { bind<GetAuthLinkUseCase>() }
}