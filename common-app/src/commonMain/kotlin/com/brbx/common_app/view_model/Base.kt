package com.brbx.common_app.view_model

import com.brbx.common_app.model.AppEffect
import com.brbx.common_app.model.AppIntent
import com.brbx.common_app.model.AppState
import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope

internal typealias AppMviScope = EchoFlowMviScope<AppState, AppIntent, AppEffect>

internal interface AppViewModelDelegate<in Intent : AppIntent> :
    EchoFlowMviDelegate<AppState, Intent, AppEffect>