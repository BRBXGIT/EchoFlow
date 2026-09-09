package com.brbx.feature_common.view_model

import com.brbx.mvi_core.contracts.MviScope

typealias EchoFlowMviScope<State, Intent, ScreenEffect> =
    MviScope<State, EchoFlowEffect, ScreenEffect, Intent>