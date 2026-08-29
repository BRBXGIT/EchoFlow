package com.brbx.common_app.view_model

import com.brbx.common_app.model.AppIntent
import com.brbx.common_app.view_model.base.AppMviScope
import com.brbx.common_app.view_model.base.AppViewModelDelegate
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.mvi_core.helpers.bindLatest

internal interface AuthStateDelegate : AppViewModelDelegate<AppIntent.BindAuthState>

internal class AuthStateDelegateImpl(
    override val scope: AppMviScope,
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase,
) : AuthStateDelegate {
    override fun invoke(intent: AppIntent.BindAuthState) {
        getUserAuthStateUseCase() bindLatest { copy(authState = it) }
    }
}