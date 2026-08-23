package com.brbx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule

@Composable
fun rememberNavigator(
    serializers: SerializersModule,
    startKey: EchoFowNavKey,
): Navigator {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = serializers
        },
        startKey,
    )
    return NavigatorImpl(backStack)
}

internal class NavigatorImpl(
    private val backStack: NavBackStack<NavKey>,
) : Navigator {

    override val currentDestination: EchoFowNavKey?
        get() = backStack.lastOrNull() as? EchoFowNavKey

    override fun navigate(key: EchoFowNavKey) {
        backStack.add(key)
    }

    override fun navigateBack() {
        backStack.removeFirstOrNull()
    }

    override fun removePrevious() {
        backStack.removeLastOrNull()
    }
}