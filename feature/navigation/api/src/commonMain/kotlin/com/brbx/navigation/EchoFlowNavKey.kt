package com.brbx.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
interface EchoFlowNavKey : NavKey

@Serializable
interface TopLevelNavKey : EchoFlowNavKey {
    val textRes: StringResource
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

interface ExternalUriNavKey : EchoFlowNavKey {
    val uri: String
}