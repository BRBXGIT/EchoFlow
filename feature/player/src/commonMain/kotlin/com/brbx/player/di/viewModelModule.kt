package com.brbx.player.di

import com.brbx.feature_common.view_model.delegateOf
import com.brbx.player.view_model.PlaybackController
import com.brbx.player.view_model.PlaybackControllerImpl
import com.brbx.player.view_model.PlayerViewModel
import com.brbx.player.view_model.SeekController
import com.brbx.player.view_model.SeekControllerImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

internal val viewModelModule = module {
    delegateOf(::PlaybackControllerImpl) bind PlaybackController::class
    delegateOf(::SeekControllerImpl) bind SeekController::class

    viewModel<PlayerViewModel>()
}