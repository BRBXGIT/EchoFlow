package com.brbx.home.view_model

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.home.model.HomeIntent
import com.brbx.home.model.HomeState

internal typealias HomeMviScope = EchoFlowMviScope<HomeState, HomeIntent, Unit>

internal interface HomeViewModelDelegate<Intent : HomeIntent> :
        EchoFlowMviDelegate<HomeState, Intent, Unit>