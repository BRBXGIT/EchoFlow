package com.brbx.core_common.dispatchers

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal val dispatchersModule = module {
    single(qualifier = DispatcherQualifier.Main) { Dispatchers.Main }
    single(qualifier = DispatcherQualifier.Io) { Dispatchers.IO }
    single(qualifier = DispatcherQualifier.Default) { Dispatchers.Default }
    single(qualifier = DispatcherQualifier.Unconfined) { Dispatchers.Unconfined }
}