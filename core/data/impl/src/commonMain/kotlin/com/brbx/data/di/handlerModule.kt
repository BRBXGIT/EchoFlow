package com.brbx.data.di

import com.brbx.data.handler.AuthDeeplinkHandler
import com.brbx.data.handler.AuthDeeplinkHandlerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val handlerModule = module { 
    singleOf(constructor = ::AuthDeeplinkHandlerImpl) { bind<AuthDeeplinkHandler>() }
}