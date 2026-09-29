package com.brbx.feature_common.view_model

import com.brbx.mvi_core.contracts.IntentDelegate
import com.brbx.mvi_core.contracts.MviDelegate
import com.brbx.mvi_core.contracts.MviScope

typealias EchoFlowMviScope<State, Intent, ScreenEffect> =
        MviScope<State, EchoFlowEffect, ScreenEffect, Intent>

interface EchoFlowIntentDelegate<State, in Intent : Any, ScreenEffect> :
        IntentDelegate<State, EchoFlowEffect, ScreenEffect, Intent>