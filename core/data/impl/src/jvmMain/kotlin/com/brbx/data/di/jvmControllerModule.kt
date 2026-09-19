package com.brbx.data.di

import com.brbx.core_common.dispatchers.getMainImmediateDispatcher
import com.brbx.data.controller.JvmPlayerController
import com.brbx.data.controller.PlayerController
import org.koin.dsl.module

internal val jvmControllerModule = module {
    single<PlayerController> {
        JvmPlayerController(
            dispatcherMain = getMainImmediateDispatcher(),
        )
    }
}
