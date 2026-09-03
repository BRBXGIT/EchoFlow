package com.brbx.network.di

import com.brbx.network.handler.AuthResponseHandler
import com.brbx.network.handler.AuthResponseHandlerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val handlerModule = module {
    singleOf(constructor = ::AuthResponseHandlerImpl) { bind<AuthResponseHandler>() }
}