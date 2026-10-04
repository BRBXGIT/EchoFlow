package com.brbx.data.di

import com.brbx.core_common.dispatchers.getMainImmediateDispatcher
import com.brbx.data.controller.AndroidPlayerController
import com.brbx.data.controller.MediaServiceIntentProvider
import com.brbx.data.controller.MediaServiceIntentProviderImpl
import com.brbx.data.controller.PlayerController
import com.brbx.data.provider.PlayerProvider
import com.brbx.data.provider.PlayerProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val androidControllerModule = module {
    singleOf(::PlayerProviderImpl) bind PlayerProvider::class
    single<PlayerController> {
        AndroidPlayerController(
            playerProvider = get(),
            dispatcherMain = getMainImmediateDispatcher(),
        )
    }
    singleOf(::MediaServiceIntentProviderImpl) bind MediaServiceIntentProvider::class
}