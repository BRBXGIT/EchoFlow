package com.brbx.domain.di

import com.brbx.domain.use_case.AuthenticateUserUseCase
import com.brbx.domain.use_case.AuthenticateUserUseCaseImpl
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.domain.use_case.GetUserAuthStateUseCaseImpl
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCase
import com.brbx.domain.use_case.GetUserRecentlyTracksUseCaseImpl
import com.brbx.domain.use_case.GetUserRelatedTracksUseCase
import com.brbx.domain.use_case.GetUserRelatedTracksUseCaseImpl
import com.brbx.domain.use_case.ObservePlayerStateUseCase
import com.brbx.domain.use_case.ObservePlayerStateUseCaseImpl
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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(constructor = ::GetUserAuthStateUseCaseImpl) { bind<GetUserAuthStateUseCase>() }
    singleOf(constructor = ::AuthenticateUserUseCaseImpl) { bind<AuthenticateUserUseCase>() }
    singleOf(constructor = ::GetUserRecentlyTracksUseCaseImpl) { bind<GetUserRecentlyTracksUseCase>() }
    singleOf(constructor = ::GetUserRelatedTracksUseCaseImpl) { bind<GetUserRelatedTracksUseCase>() }
    singleOf(constructor = ::ObservePlayerStateUseCaseImpl) { bind<ObservePlayerStateUseCase>() }
    singleOf(constructor = ::PausePlaybackUseCaseImpl) { bind<PausePlaybackUseCase>() }
    singleOf(constructor = ::ResumePlaybackUseCaseImpl) { bind<ResumePlaybackUseCase>() }
    singleOf(constructor = ::SeekToPositionUseCaseImpl) { bind<SeekToPositionUseCase>() }
    singleOf(constructor = ::SetPlaybackQueueUseCaseImpl) { bind<SetPlaybackQueueUseCase>() }
    singleOf(constructor = ::SkipToNextTrackUseCaseImpl) { bind<SkipToNextTrackUseCase>() }
    singleOf(constructor = ::SkipToPreviousTrackUseCaseImpl) { bind<SkipToPreviousTrackUseCase>() }
}

