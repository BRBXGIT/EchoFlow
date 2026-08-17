package com.brbx.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.materialkolor.rememberDynamicColorScheme

@Composable
fun AppTheme(
    seedColor: Color = EchoFlowSeedColor,
    isDark: Boolean = isSystemInDarkTheme(),
    motion: EchoFlowMotion = DefaultMotion(),
    content: @Composable () -> Unit,
) {
    val dynamicColorScheme = rememberDynamicColorScheme(
        seedColor = seedColor,
        isDark = isDark,
    )

    CompositionLocalProvider(
        LocalMotion provides motion,
    ) {
        MaterialTheme(
            colorScheme = dynamicColorScheme,
            typography = echoFlowType(),
            motionScheme = LocalMotion.current,
            content = content,
        )
    }
}

val mMotion @Composable @ReadOnlyComposable get() = MaterialTheme.motionScheme
val mColors @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme
val mTypography @Composable @ReadOnlyComposable get() = MaterialTheme.typography
val mShapes @Composable @ReadOnlyComposable get() = MaterialTheme.shapes