package com.brbx.domain.di

import com.brbx.domain.use_case.AuthenticateUserUseCase
import com.brbx.domain.use_case.AuthenticateUserUseCaseImpl
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.domain.use_case.GetUserAuthStateUseCaseImpl
import com.brbx.domain.use_case.GetUserRecentlyPlayedSnapshotUseCase
import com.brbx.domain.use_case.GetUserRecentlyPlayedSnapshotUseCaseImpl
import com.brbx.domain.use_case.GetUserRecentlyPlayedUseCase
import com.brbx.domain.use_case.GetUserRecentlyPlayedUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(constructor = ::GetUserAuthStateUseCaseImpl) { bind<GetUserAuthStateUseCase>() }
    singleOf(constructor = ::AuthenticateUserUseCaseImpl) { bind<AuthenticateUserUseCase>() }
    singleOf(constructor = ::GetUserRecentlyPlayedUseCaseImpl) { bind<GetUserRecentlyPlayedUseCase>() }
    singleOf(constructor = ::GetUserRecentlyPlayedSnapshotUseCaseImpl) { bind<GetUserRecentlyPlayedSnapshotUseCase>() }
}