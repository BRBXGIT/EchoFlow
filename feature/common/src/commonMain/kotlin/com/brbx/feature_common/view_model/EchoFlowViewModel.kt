package com.brbx.feature_common.view_model

import com.brbx.mvi_core.base.ContainedMviViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope

abstract class EchoFlowViewModel<State, in Intent : Any, ScreenEffect : Any>(
    initialState: State,
    effectReplay: Int = 1,
) : ContainedMviViewModel<State, EchoFlowEffect, ScreenEffect, Intent>(initialState, effectReplay),
    KoinComponent

abstract class ScopedEchoFlowViewModel<State, in Intent : Any, ScreenEffect : Any>(
    initialState: State,
    effectReplay: Int = 1,
) : EchoFlowViewModel<State, Intent, ScreenEffect>(initialState, effectReplay),
    KoinScopeComponent {

    final override val scope by lazy { createScope(source = this) }

    override fun onCleared() {
        scope.close()
        super.onCleared()
    }
}