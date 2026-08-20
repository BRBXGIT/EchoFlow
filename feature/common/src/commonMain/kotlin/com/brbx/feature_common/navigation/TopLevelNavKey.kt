package com.brbx.feature_common.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

@Immutable
interface TopLevelNavKey : NavKey {
    val label: StringResource
    val selectedIcon: ImageVector
    val defaultIcon: ImageVector
}