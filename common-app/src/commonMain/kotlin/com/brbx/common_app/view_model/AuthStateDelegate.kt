package com.brbx.common_app.view_model

import com.brbx.common_app.model.AppIntent

internal interface AuthStateDelegate : AppViewModelDelegate<AppIntent.BindAuthState>

internal class AuthStateDelegateImpl(
    override val scope: AppViewModelMviScope,
) : AuthStateDelegate {
    override fun invoke(intent: AppIntent.BindAuthState) {
        TODO("Not yet implemented")
    }
}