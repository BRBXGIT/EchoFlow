package com.brbx.design_system.components.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.window.core.layout.WindowSizeClass

// Maybe will be rewritten from Boolean to WindowSizeClass
@Composable
fun rememberIsLargeScreen() =
    currentWindowAdaptiveInfoV2().let { adaptiveInfo ->
        remember(key1 = adaptiveInfo) {
            adaptiveInfo
                .windowSizeClass
                .isWidthAtLeastBreakpoint(widthDpBreakpoint = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
        }
    }