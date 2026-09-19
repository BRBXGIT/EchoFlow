package com.brbx.data.di

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.brbx.data.controller.AndroidPlayerController
import com.brbx.data.controller.PlayerController
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val controllerModule = module {
    single<Player> { ExoPlayer.Builder(androidContext()).build() }
    singleOf(constructor = ::AndroidPlayerController) { bind<PlayerController>() }
}