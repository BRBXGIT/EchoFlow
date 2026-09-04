package com.brbx.home.view_model.base

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.home.model.HomeIntent
import com.brbx.home.model.HomeState

internal interface HomeViewModelDelegate<Intent : HomeIntent> : EchoFlowMviDelegate<HomeState, Intent, Unit>