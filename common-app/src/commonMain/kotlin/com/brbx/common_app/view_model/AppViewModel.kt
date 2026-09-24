package com.brbx.common_app.view_model

import androidx.compose.runtime.Stable
import com.brbx.common_app.model.AppEffect
import com.brbx.common_app.model.AppIntent
import com.brbx.common_app.model.AppState
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate
import com.brbx.mvi_core.helpers.shareInEagerly
import com.brbx.mvi_core.helpers.stateInEagerly

@Stable
internal class AppViewModel : EchoFlowViewModel<AppState, AppIntent, AppEffect>(
    initialState = AppState(),
) {
    private val authStateDelegate by injectDelegate<AuthStateDelegate>()

    init { authStateDelegate }

    override val state = _state.stateInEagerly(initialValue = AppState())
    override val effects = _effects.shareInEagerly()
    override val screenEffects = _screenEffects.shareInEagerly()

    override fun dispatchIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.AuthState -> authStateDelegate(intent)
        }
    }
}