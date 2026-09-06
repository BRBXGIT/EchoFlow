package com.brbx.data.di

import com.brbx.data.handler.AuthCallbackHandler
import com.brbx.data.handler.AuthCallbackHandlerImpl
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.handler.NetworkResponseHandlerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val handlerModule = module {
    singleOf(constructor = ::AuthCallbackHandlerImpl) { bind<AuthCallbackHandler>() }
    singleOf(constructor = ::NetworkResponseHandlerImpl) { bind<NetworkResponseHandler>() }
}
