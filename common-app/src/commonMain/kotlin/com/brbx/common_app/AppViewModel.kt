package com.brbx.common_app

import androidx.compose.runtime.Stable
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.mvi_core.helpers.stateInEagerly

@Stable
internal class AppViewModel : EchoFlowViewModel<AppState, Unit, Unit>(
    initialState = AppState(),
) {
    override val state = _state.stateInEagerly(initialValue = AppState())
}