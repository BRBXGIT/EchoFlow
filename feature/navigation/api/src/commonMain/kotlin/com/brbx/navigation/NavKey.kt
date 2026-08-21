package com.brbx.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import androidx.navigation3.runtime.NavKey as Nav3Key

interface NavKey : Nav3Key

interface TopLevelNavKey : NavKey {
    val textRes: StringResource
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

interface ExternalUriNavKey : NavKey {
    val uri: String
}