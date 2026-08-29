package com.brbx.domain.di

import com.brbx.domain.use_case.GetAuthLinkUseCase
import com.brbx.domain.use_case.GetAuthLinkUseCaseImpl
import com.brbx.domain.use_case.GetAuthPayloadUseCase
import com.brbx.domain.use_case.GetAuthPayloadUseCaseImpl
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.domain.use_case.GetUserAuthStateUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(constructor = ::GetUserAuthStateUseCaseImpl) { bind<GetUserAuthStateUseCase>() }
    singleOf(constructor = ::GetAuthLinkUseCaseImpl) { bind<GetAuthLinkUseCase>() }
    singleOf(constructor = ::GetAuthPayloadUseCaseImpl) { bind<GetAuthPayloadUseCase>() }
}