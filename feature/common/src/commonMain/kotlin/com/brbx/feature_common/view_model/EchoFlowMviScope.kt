package com.brbx.feature_common.view_model

import com.brbx.mvi_core.contracts.MviScope

interface EchoFlowMviScope<State, in Intent : Any, ScreenEffect : Any> :
    MviScope<State, EchoFlowEffect, ScreenEffect, Intent>