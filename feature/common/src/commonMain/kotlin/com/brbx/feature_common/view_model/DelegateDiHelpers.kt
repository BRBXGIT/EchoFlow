package com.brbx.feature_common.view_model

import org.koin.core.component.inject
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

inline fun <reified T : Any, reified S : EchoFlowMviScope<*, *, *>> Module.viewModelDelegate(
    crossinline factoryBlock: Scope.(mviScope: S) -> T,
): KoinDefinition<T> = factory<T> { params ->
    this.factoryBlock(params.get())
}

inline fun <reified R : Any, reified T1 : EchoFlowMviScope<*, *, *>> Module.delegateOf(
    crossinline constructor: (T1) -> R,
): KoinDefinition<R> = factory<R> { params -> constructor(params.get()) }

inline fun <reified R : Any, reified T1 : EchoFlowMviScope<*, *, *>, reified T2> Module.delegateOf(
    crossinline constructor: (T1, T2) -> R,
): KoinDefinition<R> = factory<R> { params -> constructor(params.get(), get()) }

inline fun <reified R : Any, reified T1 : EchoFlowMviScope<*, *, *>, reified T2, reified T3> Module.delegateOf(
    crossinline constructor: (T1, T2, T3) -> R,
): KoinDefinition<R> = factory<R> { params -> constructor(params.get(), get(), get()) }

inline fun <reified R : Any, reified T1 : EchoFlowMviScope<*, *, *>, reified T2, reified T3, reified T4> Module.delegateOf(
    crossinline constructor: (T1, T2, T3, T4) -> R,
): KoinDefinition<R> = factory<R> { params -> constructor(params.get(), get(), get(), get()) }

inline fun <reified R : Any, reified T1 : EchoFlowMviScope<*, *, *>, reified T2, reified T3, reified T4, reified T5> Module.delegateOf(
    crossinline constructor: (T1, T2, T3, T4, T5) -> R,
): KoinDefinition<R> = factory<R> { params -> constructor(params.get(), get(), get(), get(), get()) }

inline fun <reified D : Any> EchoFlowViewModel<*, *, *>.injectDelegate(): Lazy<D> =
    this.inject { parametersOf(this.scope) }