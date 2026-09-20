package com.brbx.feature_common.view_model

import com.brbx.mvi_core.contracts.MviDelegate
import com.brbx.mvi_core.contracts.MviScope

interface EchoFlowMviDelegate<State, in Intent : Any, ScreenEffect> :
        MviDelegate<State, EchoFlowEffect, ScreenEffect, Intent>

typealias EchoFlowMviScope<State, Intent, ScreenEffect> =
        MviScope<State, EchoFlowEffect, ScreenEffect, Intent>