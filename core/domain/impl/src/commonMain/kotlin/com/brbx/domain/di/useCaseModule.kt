package com.brbx.domain.di

import com.brbx.domain.use_case.AddToPlaybackQueueUseCase
import com.brbx.domain.use_case.AddToPlaybackQueueUseCaseImpl
import com.brbx.domain.use_case.AuthenticateUserUseCase
import com.brbx.domain.use_case.AuthenticateUserUseCaseImpl
import com.brbx.domain.use_case.GetDailyMixUseCase
import com.brbx.domain.use_case.GetDailyMixUseCaseImpl
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.domain.use_case.GetUserAuthStateUseCaseImpl
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCase
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCaseImpl
import com.brbx.domain.use_case.GetPlayerStateUseCase
import com.brbx.domain.use_case.GetPlayerStateUseCaseImpl
import com.brbx.domain.use_case.PausePlaybackUseCase
import com.brbx.domain.use_case.PausePlaybackUseCaseImpl
import com.brbx.domain.use_case.ResumePlaybackUseCase
import com.brbx.domain.use_case.ResumePlaybackUseCaseImpl
import com.brbx.domain.use_case.SeekToPositionUseCase
import com.brbx.domain.use_case.SeekToPositionUseCaseImpl
import com.brbx.domain.use_case.SetPlaybackQueueUseCase
import com.brbx.domain.use_case.SetPlaybackQueueUseCaseImpl
import com.brbx.domain.use_case.SkipToNextTrackUseCase
import com.brbx.domain.use_case.SkipToNextTrackUseCaseImpl
import com.brbx.domain.use_case.SkipToPreviousTrackUseCase
import com.brbx.domain.use_case.SkipToPreviousTrackUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetUserAuthStateUseCaseImpl) bind GetUserAuthStateUseCase::class
    factoryOf(::AuthenticateUserUseCaseImpl) bind AuthenticateUserUseCase::class
    singleOf(::GetUserRecentlyTracksUseCaseImpl) bind GetUserRecentlyTracksUseCase::class
    singleOf(::GetDailyMixUseCaseImpl) bind GetDailyMixUseCase::class
    singleOf(::GetPlayerStateUseCaseImpl) bind GetPlayerStateUseCase::class
    singleOf(::PausePlaybackUseCaseImpl) bind PausePlaybackUseCase::class
    singleOf(::ResumePlaybackUseCaseImpl) bind ResumePlaybackUseCase::class
    singleOf(::SeekToPositionUseCaseImpl) bind SeekToPositionUseCase::class
    singleOf(::SetPlaybackQueueUseCaseImpl) bind SetPlaybackQueueUseCase::class
    singleOf(::SkipToNextTrackUseCaseImpl) bind SkipToNextTrackUseCase::class
    singleOf(::SkipToPreviousTrackUseCaseImpl) bind SkipToPreviousTrackUseCase::class
    singleOf(::AddToPlaybackQueueUseCaseImpl) bind AddToPlaybackQueueUseCase::class
}

