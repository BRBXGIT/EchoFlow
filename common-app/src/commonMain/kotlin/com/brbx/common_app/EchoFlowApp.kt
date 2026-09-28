package com.brbx.common_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brbx.common_app.model.AppEffect
import com.brbx.common_app.view_model.AppViewModel
import com.brbx.design_system.components.components.LocalCurrentPlayingTrackId
import com.brbx.design_system.components.components.LocalCurrentPlayingTrackPaused
import com.brbx.domain.model.enums.PlaybackStatus
import com.brbx.feature_common.composable.HandleEchoFlowEffects
import com.brbx.navigation.EchoFlowNavGraph
import com.brbx.navigation.LocalNavigator
import com.brbx.navigation.Navigator
import com.brbx.navigation.rememberNavigator
import com.brbx.player.view_model.PlayerViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EchoFlowApp(deeplink: String?) {
    val appViewModel = koinViewModel<AppViewModel>()
    val appState by appViewModel.state.collectAsStateWithLifecycle()
    val startKey = appState.startKey(deeplink) ?: return

    val playerViewModel = koinViewModel<PlayerViewModel>()
    val playerState by playerViewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberNavigator(
        serializers = rememberSerializers(),
        startKey = startKey,
    )

    HandleAppEffects(appViewModel.screenEffects, navigator)
    HandleEchoFlowEffects(appViewModel.effects, navigator = navigator)

    val isPlaying = remember(key1 = playerState.status) { playerState.status == PlaybackStatus.Playing }
    CompositionLocalProvider(
        LocalNavigator provides navigator,
        LocalCurrentPlayingTrackId provides playerState.currentTrack?.id,
        LocalCurrentPlayingTrackPaused provides isPlaying,
    ) {
        EchoFlowNavGraph()
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