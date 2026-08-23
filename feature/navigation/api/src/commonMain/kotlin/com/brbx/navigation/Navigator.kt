package com.brbx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

@Stable
interface Navigator {
    val currentDestination: EchoFlowNavKey
    val currentBackstack: List<EchoFlowNavKey>

    fun navigate(key: EchoFlowNavKey)
    fun navigateBack()
    fun removePrevious()
}

val LocalNavigator = compositionLocalOf<Navigator> { error("No navigator provided") }
val echoFlowNavigator @Composable @ReadOnlyComposable get() = LocalNavigator.current