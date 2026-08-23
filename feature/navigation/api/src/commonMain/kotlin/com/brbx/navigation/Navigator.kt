package com.brbx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

@Stable
interface Navigator {
    val currentDestination: EchoFowNavKey
    val currentBackstack: List<EchoFowNavKey>

    fun navigate(key: EchoFowNavKey)
    fun navigateBack()
    fun removePrevious()
}

val LocalNavigator = compositionLocalOf<Navigator> { error("No navigator provided") }
val navigator @Composable @ReadOnlyComposable get() = LocalNavigator.current