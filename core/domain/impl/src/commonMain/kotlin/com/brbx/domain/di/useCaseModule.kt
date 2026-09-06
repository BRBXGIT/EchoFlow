package com.brbx.domain.di

import com.brbx.domain.use_case.AuthenticateUserUseCase
import com.brbx.domain.use_case.AuthenticateUserUseCaseImpl
import com.brbx.domain.use_case.GetPagedRecentTracksUseCase
import com.brbx.domain.use_case.GetPagedRecentTracksUseCaseImpl
import com.brbx.domain.use_case.GetRecentTracksUseCase
import com.brbx.domain.use_case.GetRecentTracksUseCaseImpl
import com.brbx.domain.use_case.GetPagedRelatedTracksUseCase
import com.brbx.domain.use_case.GetPagedRelatedTracksUseCaseImpl
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.domain.use_case.GetUserAuthStateUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(constructor = ::GetUserAuthStateUseCaseImpl) { bind<GetUserAuthStateUseCase>() }
    singleOf(constructor = ::AuthenticateUserUseCaseImpl) { bind<AuthenticateUserUseCase>() }
    singleOf(constructor = ::GetRecentTracksUseCaseImpl) { bind<GetRecentTracksUseCase>() }
    singleOf(constructor = ::GetPagedRecentTracksUseCaseImpl) { bind<GetPagedRecentTracksUseCase>() }
    singleOf(constructor = ::GetPagedRelatedTracksUseCaseImpl) { bind<GetPagedRelatedTracksUseCase>() }
}
