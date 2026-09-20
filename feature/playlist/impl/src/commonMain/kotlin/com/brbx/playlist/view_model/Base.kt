package com.brbx.playlist.view_model

import com.brbx.feature_common.view_model.EchoFlowMviDelegate
import com.brbx.feature_common.view_model.EchoFlowMviScope
import com.brbx.playlist.model.PlaylistIntent
import com.brbx.playlist.model.PlaylistState

internal typealias PlaylistMviScope =
        EchoFlowMviScope<PlaylistState, PlaylistIntent, Unit>

internal interface PlaylistViewModelDelegate<Intent : PlaylistIntent> :
        EchoFlowMviDelegate<PlaylistState, Intent, Unit>