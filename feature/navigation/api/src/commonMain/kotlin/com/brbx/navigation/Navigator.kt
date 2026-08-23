package com.brbx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

interface Navigator {
    val currentDestination: EchoFowNavKey?

    fun navigate(key: EchoFowNavKey)
    fun navigateBack()
    fun removePrevious()
}

val LocalNavigator = compositionLocalOf<Navigator> { error("No navigator provided") }
val navigator @Composable @ReadOnlyComposable get() = LocalNavigator.current