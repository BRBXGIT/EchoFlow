package com.brbx.common_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.design_system.theme.EchoFlowTheme
import com.brbx.domain.model.UserAuthState
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

    val navigator = rememberNavigator(
        serializers = rememberSerializers(),
        startKey = rememberStartKey(state.authState),
    )
    CompositionLocalProvider(value = LocalNavigator provides navigator) {
        EchoFlowTheme {
            EchoFlowNavGraph()
        }
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
private fun rememberStartKey(authState: UserAuthState): EchoFlowNavKey =
    remember {
        if (authState == UserAuthState.Unauthorized) OnboardingRoute else OnboardingRoute
    }