package com.brbx.feature_common.view_model

import com.brbx.mvi_core.base.ContainedMviViewModel
import org.koin.core.component.KoinComponent

abstract class EchoFlowViewModel<State, in Intent : Any, ScreenEffect : Any>(
    initialState: State,
    effectReplay: Int = 1,
) : ContainedMviViewModel<State, EchoFlowEffect, ScreenEffect, Intent>(initialState, effectReplay),
    KoinComponent