package com.brbx.feature_common.view_model

import com.brbx.mvicore.contracts.MviDelegate

interface EchoFlowMviDelegate<State, in Intent : Any> :
    MviDelegate<State, EchoFlowEffect, Unit, Intent>