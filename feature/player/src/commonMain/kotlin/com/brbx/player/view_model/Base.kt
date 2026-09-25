package com.brbx.player.view_model

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.player.model.PlayerIntent
import com.brbx.player.model.UiPlayerState

internal typealias PlayerMviScope = EchoFlowMviScope<UiPlayerState, PlayerIntent, Unit>

internal interface PlayerDelegate<in Intent : PlayerIntent> :
        EchoFlowMviDelegate<UiPlayerState, Intent, Unit>