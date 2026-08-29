package com.brbx.common_app.view_model.base

import com.brbx.common_app.model.AppIntent
import com.brbx.common_app.model.AppState
import com.brbx.feature_common.view_model.EchoFlowMviDelegate

internal interface AppViewModelDelegate<in Intent : AppIntent> :
    EchoFlowMviDelegate<AppState, Intent, Unit>