package com.brbx.feature_common

import com.brbx.mvicore.base.ContainedMviViewModel

abstract class EchoFlowViewModel<State, in Intent : Any>(
    initialState: State,
    effectReplay: Int = 1,
) : ContainedMviViewModel<State, EchoFlowEffect, Intent>(initialState, effectReplay)