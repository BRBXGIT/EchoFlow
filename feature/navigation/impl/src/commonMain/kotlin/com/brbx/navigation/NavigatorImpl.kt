package com.brbx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule

@Composable
fun rememberNavigator(
    serializers: SerializersModule,
    startKey: EchoFlowNavKey,
): Navigator {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = serializers
        },
        startKey,
    )
    return remember(key1 = backStack) {
        NavigatorImpl(backStack)
    }
}

internal class NavigatorImpl(
    private val backStack: NavBackStack<NavKey>,
) : Navigator {

    override val currentDestination: EchoFlowNavKey
        get() = requireNotNull(backStack.lastOrNull() as? EchoFlowNavKey)

    @Suppress("UNCHECKED_CAST")
    override val currentBackstack: List<EchoFlowNavKey>
        get() = backStack as List<EchoFlowNavKey>

    override fun navigate(key: EchoFlowNavKey) {
        backStack.add(key)
    }

    override fun navigateBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    override fun removePrevious() {
        if (backStack.size > 1) backStack.removeAt(index = backStack.lastIndex - 1)
    }

    override fun removeAllExceptCurrent() {
        while (backStack.size > 1) {
            backStack.removeAt(index = 0)
        }
    }
}