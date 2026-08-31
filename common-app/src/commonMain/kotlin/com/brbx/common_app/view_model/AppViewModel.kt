package com.brbx.common_app.view_model

import androidx.compose.runtime.Stable
import com.brbx.common_app.model.AppIntent
import com.brbx.common_app.model.AppState
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.mvi_core.helpers.stateInEagerly

@Stable
internal class AppViewModel : EchoFlowViewModel<AppState, AppIntent, Unit>(
    initialState = AppState(),
) {
    private val authStateDelegate by injectDelegate<AuthStateDelegate>()

    init {
        dispatchIntent(AppIntent.BindAuthState)
    }

    override val state = _state.stateInEagerly(initialValue = AppState())

    override fun dispatchIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.BindAuthState -> authStateDelegate(intent)
        }
    }
}