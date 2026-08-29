package com.brbx.common_app.model

internal sealed interface AppIntent {
    data object BindAuthState : AppIntent
}