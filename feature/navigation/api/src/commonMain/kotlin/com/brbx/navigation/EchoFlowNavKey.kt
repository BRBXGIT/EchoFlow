package com.brbx.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import androidx.navigation3.runtime.NavKey

interface EchoFlowNavKey : NavKey

interface TopLevelNavKey : EchoFlowNavKey {
    val textRes: StringResource
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

interface ExternalUriNavKey : EchoFlowNavKey {
    val uri: String
}