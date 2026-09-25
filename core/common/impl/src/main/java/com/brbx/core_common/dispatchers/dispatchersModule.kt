package com.brbx.core_common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal val dispatchersModule = module {
    single<CoroutineDispatcher>(qualifier = DispatcherQualifier.Main) { Dispatchers.Main }
    single<CoroutineDispatcher>(qualifier = DispatcherQualifier.MainImmediate) { Dispatchers.Main.immediate }
    single<CoroutineDispatcher>(qualifier = DispatcherQualifier.Io) { Dispatchers.IO }
    single<CoroutineDispatcher>(qualifier = DispatcherQualifier.Default) { Dispatchers.Default }
    single<CoroutineDispatcher>(qualifier = DispatcherQualifier.Unconfined) { Dispatchers.Unconfined }

    // Fallback
    single<CoroutineDispatcher> { Dispatchers.Default }
}