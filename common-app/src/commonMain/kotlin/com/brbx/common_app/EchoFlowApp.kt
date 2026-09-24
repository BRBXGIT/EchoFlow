package com.brbx.common_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.common_app.model.AppEffect
import com.brbx.common_app.view_model.AppViewModel
import com.brbx.design_system.theme.EchoFlowTheme
import com.brbx.feature_common.composable.HandleEchoFlowEffects
import com.brbx.navigation.EchoFlowNavGraph
import com.brbx.navigation.LocalNavigator
import com.brbx.navigation.Navigator
import com.brbx.navigation.rememberNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EchoFlowApp(deeplink: String?) {
    val viewModel = koinViewModel<AppViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val startKey = state.startKey(deeplink) ?: return

    val navigator = rememberNavigator(
        serializers = rememberSerializers(),
        startKey = startKey,
    )

    HandleAppEffects(viewModel.screenEffects, navigator)
    HandleEchoFlowEffects(viewModel.effects, navigator = navigator)

    CompositionLocalProvider(value = LocalNavigator provides navigator) {
        EchoFlowTheme {
            EchoFlowNavGraph()
        }
    }
}

@Composable
private fun HandleAppEffects(
    effects: SharedFlow<AppEffect>,
    navigator: Navigator,
) =
    LaunchedEffect(key1 = effects) {
        effects.collect { effect ->
            when (effect) {
                AppEffect.RemoveAllKeysExceptCurrent -> navigator.removeAllExceptCurrent()
            }
        }
    }

@Composable
private fun rememberSerializers(): SerializersModule {
    val serializers = getKoin().getAll<SerializersModule>()
    return remember(key1 = serializers) {
        SerializersModule {
            serializers.forEach { include(module = it) }
        }
    }
}