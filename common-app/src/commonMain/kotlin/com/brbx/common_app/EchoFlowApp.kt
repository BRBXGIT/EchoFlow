package com.brbx.common_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.brbx.design_system.theme.EchoFlowTheme
import com.brbx.navigation.EchoFlowNavGraph
import com.brbx.navigation.LocalNavigator
import com.brbx.navigation.rememberNavigator
import com.brbx.onboarding.OnboardingRoute
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.getKoin

@Composable
fun EchoFlowApp() {
    val navigator = rememberNavigator(
        serializers = rememberSerializers(),
        startKey = OnboardingRoute,
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