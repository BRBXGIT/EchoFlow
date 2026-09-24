package com.brbx.common_app.model

internal sealed interface AppIntent {
    sealed interface AuthState : AppIntent {
        data object BindAuthState : AuthState
        data object ObserveAuthState : AuthState
    }
}