package com.brbx.feature_common.view_model

import com.brbx.mvi_core.contracts.MviDelegate

typealias EchoFlowMviDelegate<State, Intent, ScreenEffect> =
        MviDelegate<State, EchoFlowEffect, ScreenEffect, Intent>