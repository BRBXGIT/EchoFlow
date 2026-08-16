package com.brbx.core_common.dispatchers

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.core.scope.Scope
import kotlinx.coroutines.CoroutineDispatcher

enum class DispatcherQualifier : Qualifier {
    Main, Io, Default, Unconfined;

    override val value: QualifierValue = this.name
}

fun Scope.getMainDispatcher(): CoroutineDispatcher = get(qualifier = DispatcherQualifier.Main)
fun Scope.getIoDispatcher(): CoroutineDispatcher = get(qualifier = DispatcherQualifier.Io)
fun Scope.getDefaultDispatcher(): CoroutineDispatcher = get(qualifier = DispatcherQualifier.Default)
fun Scope.getUnconfinedDispatcher(): CoroutineDispatcher = get(qualifier = DispatcherQualifier.Unconfined)