package com.brbx.feature_common.view_model

import com.brbx.mvi_core.contracts.MviDelegate

interface EchoFlowMviDelegate<State, in Intent : Any, ScreenEffect : Any> :
    MviDelegate<State, EchoFlowEffect, ScreenEffect, Intent>