package com.brbx.common_app.view_model

import com.brbx.common_app.model.AppEffect
import com.brbx.common_app.model.AppIntent
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.domain.use_case.GetUserAuthStateUseCase
import com.brbx.feature_common.view_model.EchoFlowEffect
import com.brbx.home.HomeRoute
import com.brbx.mvi_core.helpers.bindLatest
import com.brbx.mvi_core.helpers.collectFlowLatest
import com.brbx.mvi_core.helpers.postEffect
import com.brbx.mvi_core.helpers.postScreenEffect
import com.brbx.navigation.EchoFlowNavKey
import com.brbx.onboarding.OnboardingRoute
import kotlinx.coroutines.CoroutineDispatcher

internal interface AuthStateDelegate : AppViewModelDelegate<AppIntent.AuthState>

internal class AuthStateDelegateImpl(
    override val mviScope: AppMviScope,
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase,
    private val dispatcherIo: CoroutineDispatcher,
) : AuthStateDelegate {

    init {
        bindAuthState()
        observeAuthState()
    }

    override fun invoke(intent: AppIntent.AuthState) {
        when (intent) {
            AppIntent.AuthState.BindAuthState -> bindAuthState()
            AppIntent.AuthState.ObserveAuthState -> observeAuthState()
        }
    }

    private fun bindAuthState() =
        getUserAuthStateUseCase().bindLatest(context = dispatcherIo) { copy(authState = it) }

    private fun observeAuthState() {
        var previous: UserAuthState? = null
        getUserAuthStateUseCase().collectFlowLatest(context = dispatcherIo) { state ->
            if (previous != null) {
                if (state == UserAuthState.Authorized && previous == UserAuthState.Unauthorized) {
                    executeAuthChange(HomeRoute)
                } else if (state == UserAuthState.Unauthorized && previous == UserAuthState.Authorized) {
                    executeAuthChange(OnboardingRoute())
                }
            }
            previous = state
        }
    }

    private fun executeAuthChange(route: EchoFlowNavKey) {
        postEffect(EchoFlowEffect.Navigate(key = route))
        postScreenEffect(AppEffect.RemoveAllKeysExceptCurrent)
    }
}