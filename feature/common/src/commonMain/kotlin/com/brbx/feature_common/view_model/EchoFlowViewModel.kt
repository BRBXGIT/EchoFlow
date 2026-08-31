package com.brbx.feature_common.view_model

import androidx.lifecycle.viewModelScope
import com.brbx.mvi_core.base.ContainedMviViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

abstract class EchoFlowViewModel<State, in Intent : Any, ScreenEffect : Any>(
    initialState: State,
    effectReplay: Int = 1,
) : ContainedMviViewModel<State, EchoFlowEffect, ScreenEffect, Intent>(initialState, effectReplay),
    KoinComponent {

    override val scope: EchoFlowMviScope<State, Intent, ScreenEffect> = object : EchoFlowMviScope<State, Intent, ScreenEffect> {
        override val viewModelScope: CoroutineScope = this@EchoFlowViewModel.viewModelScope
        override val state: StateFlow<State> = this@EchoFlowViewModel.state
        override val effects: SharedFlow<EchoFlowEffect> = this@EchoFlowViewModel.effects
        override val screenEffects: SharedFlow<ScreenEffect> = this@EchoFlowViewModel.screenEffects

        override fun reduce(reducer: State.() -> State) =
            this@EchoFlowViewModel.reduce(reducer)

        override fun dispatchIntent(intent: Intent) =
            this@EchoFlowViewModel.dispatchIntent(intent)

        override fun postEffect(effect: EchoFlowEffect) =
            this@EchoFlowViewModel.postEffect(effect)

        override fun postScreenEffect(effect: ScreenEffect) =
            this@EchoFlowViewModel.postScreenEffect(effect)
    }
}