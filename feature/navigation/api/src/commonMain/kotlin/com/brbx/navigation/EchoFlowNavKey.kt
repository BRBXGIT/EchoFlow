package com.brbx.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Immutable
@Serializable
interface EchoFlowNavKey : NavKey

@Immutable
@Serializable
interface TopLevelNavKey : EchoFlowNavKey {
    val textRes: StringResource
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}