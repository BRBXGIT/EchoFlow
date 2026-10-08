package com.brbx.data.di

import com.brbx.core_common.dispatchers.getIoDispatcher
import com.brbx.core_common.dispatchers.getMainImmediateDispatcher
import com.brbx.data.controller.AndroidPlayerController
import com.brbx.data.controller.MediaServiceIntentProvider
import com.brbx.data.controller.MediaServiceIntentProviderImpl
import com.brbx.data.controller.PlayerController
import com.brbx.data.provider.PlayerProvider
import com.brbx.data.provider.PlayerProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val androidControllerModule = module {
    single<PlayerProvider> {
        PlayerProviderImpl(
            tokensSource = get(),
            streamsLoader = get(),
            context = androidContext(),
        )
    }
    single<PlayerController> {
        AndroidPlayerController(
            playerProvider = get(),
            authRepository = get(),
            dispatcherMainImmediate = getMainImmediateDispatcher(),
            dispatcherIo = getIoDispatcher(),
        )
    }
    singleOf(::MediaServiceIntentProviderImpl) bind MediaServiceIntentProvider::class
}