package com.brbx.common_app.view_model

import androidx.compose.runtime.Stable
import com.brbx.common_app.model.AppEffect
import com.brbx.common_app.model.AppIntent
import com.brbx.common_app.model.AppState
import com.brbx.feature_common.view_model.EchoFlowViewModel
import com.brbx.feature_common.view_model.injectDelegate

@Stable
internal class AppViewModel : EchoFlowViewModel<AppState, AppIntent, AppEffect>(
    initialState = AppState(),
) {
    private val authStateDelegate by injectDelegate<AuthStateDelegate>()

    init { authStateDelegate }

    override fun dispatchIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.AuthState -> authStateDelegate(intent)
        }
    }
}