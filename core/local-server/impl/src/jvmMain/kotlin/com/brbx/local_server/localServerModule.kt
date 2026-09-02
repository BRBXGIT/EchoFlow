package com.brbx.local_server

import local_server.JvmAuthServer
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localServerModule = module {
    singleOf(constructor = ::JvmAuthServerImpl) { bind<JvmAuthServer>() }
}