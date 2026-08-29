package com.brbx.common_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.common_app.view_model.AppViewModel
import com.brbx.design_system.theme.EchoFlowTheme
import com.brbx.domain.model.enums.UserAuthState
import com.brbx.home.HomeRoute
import com.brbx.navigation.EchoFlowNavGraph
import com.brbx.navigation.EchoFlowNavKey
import com.brbx.navigation.LocalNavigator
import com.brbx.navigation.rememberNavigator
import com.brbx.onboarding.OnboardingRoute
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EchoFlowApp(deeplink: String?) {
    val viewModel = koinViewModel<AppViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val authState = state.authState

    val navigator = rememberNavigator(
        serializers = rememberSerializers(),
        startKey = rememberStartKey(authState, deeplink),
    )

    HandleAuthChanges(
        authState = authState,
        onLogOut = {
            navigator.navigate(key = OnboardingRoute())
            navigator.removeAllExceptCurrent()
        },
    )

    CompositionLocalProvider(value = LocalNavigator provides navigator) {
        EchoFlowTheme {
            EchoFlowNavGraph()
        }
    }
}

@Composable
private fun HandleAuthChanges(
    authState: UserAuthState,
    onLogOut: () -> Unit,
) {
    var previous by rememberSaveable { mutableStateOf<UserAuthState?>(value = null) }
    LaunchedEffect(key1 = authState) {
        if (authState == UserAuthState.Unauthorized && previous == UserAuthState.Authorized) {
            onLogOut()
        }
        previous = authState
    }
}

@Composable
private fun rememberSerializers(): SerializersModule {
    val serializers: List<SerializersModule> = getKoin().getAll()
    return remember(key1 = serializers) {
        SerializersModule {
            serializers.forEach { include(module = it) }
        }
    }
}

@Composable
private fun rememberStartKey(authState: UserAuthState, deeplink: String?): EchoFlowNavKey =
    remember {
        if (authState == UserAuthState.Unauthorized) OnboardingRoute(deeplink) else HomeRoute
    }