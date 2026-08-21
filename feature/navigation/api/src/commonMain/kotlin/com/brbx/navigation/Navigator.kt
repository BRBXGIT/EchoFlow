package com.brbx.navigation

import androidx.compose.runtime.compositionLocalOf

interface Navigator {
    val backStack: List<NavKey>
    val currentDestination: NavKey?
    val currentTopLevelDestination: TopLevelNavKey?
    fun navigate(key: NavKey)
    fun navigateBack()
}

val LocalNavigator = compositionLocalOf<Navigator> { error("No navigator provided") }