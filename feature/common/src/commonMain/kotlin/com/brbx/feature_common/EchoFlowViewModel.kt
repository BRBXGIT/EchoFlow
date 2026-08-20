package com.brbx.feature_common

import com.brbx.mvicore.base.ContainedMviViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

abstract class EchoFlowViewModel<State, in Intent : Any>(
    initialState: State,
    effectReplay: Int = 1,
) : ContainedMviViewModel<State, EchoFlowEffect, Unit, Intent>(initialState, effectReplay),
    KoinComponent {

    protected inline fun <reified D : Any> injectDelegate(): Lazy<D> =
        inject { parametersOf(scope) }
}