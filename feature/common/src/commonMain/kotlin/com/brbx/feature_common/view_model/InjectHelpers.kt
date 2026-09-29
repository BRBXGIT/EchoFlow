package com.brbx.feature_common.view_model

import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

inline fun <reified D : Any> EchoFlowViewModel<*, *, *>.injectDelegate(): Lazy<D> =
    this.inject { parametersOf(this.mviScope) }