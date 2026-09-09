package com.brbx.feature_common.view_model

import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

inline fun <reified T : Any, reified S : EchoFlowMviScope<*, *, *>> Module.delegateFactory(
    crossinline factoryBlock: Scope.(mviScope: S) -> T
) = factory<T> { params ->
        this.factoryBlock(params.get())
    }

inline fun <reified D : Any> EchoFlowViewModel<*, *, *>.injectDelegate(): Lazy<D> =
    this.inject { parametersOf(this.scope) }