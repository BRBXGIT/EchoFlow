package com.brbx.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import androidx.navigation3.runtime.NavKey

interface EchoFowNavKey : NavKey

interface TopLevelNavKey : EchoFowNavKey {
    val textRes: StringResource
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

interface ExternalUriNavKey : EchoFowNavKey {
    val uri: String
}