package com.brbx.data.di

import com.brbx.data.handler.AuthLinkHandler
import com.brbx.data.handler.AuthLinkHandlerImpl
import com.brbx.data.handler.NetworkResponseHandler
import com.brbx.data.handler.NetworkResponseHandlerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val handlerModule = module { 
    singleOf(constructor = ::AuthLinkHandlerImpl) { bind<AuthLinkHandler>() }
    singleOf(constructor = ::NetworkResponseHandlerImpl) { bind<NetworkResponseHandler>() }
}